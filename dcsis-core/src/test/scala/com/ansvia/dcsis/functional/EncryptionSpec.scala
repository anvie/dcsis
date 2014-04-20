package com.ansvia.dcsis.functional

import org.specs2.Specification
import org.bouncycastle.crypto.engines.TwofishEngine
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher
import org.bouncycastle.crypto.params.KeyParameter
import com.ansvia.dcsis.generator.PersonIdentityFactory
import com.ansvia.dcsis.helpers.HexHelpers._

class EncryptionSpec extends Specification {

    def is = "Encryption test should" ^
        p ^
        "person encryption key length must be 32" ! trees.encryptionKeyLength ^
        "be able to doing symetric encrypt and decrypt  using person encryption key" ! trees.encDec ^
        p ^
        end

    object trees {
        val text = "hello sinyu hello :)"

        val engine = new TwofishEngine()
        val cipher = new PaddedBufferedBlockCipher(engine)

        val fp = Array[Byte](1,2,3,4,5)
        val id = Array[Byte](6,7,8,9,10)

        val person = PersonIdentityFactory.build(fp, id, 1)
        val keys = person.keys

        def encryptionKeyLength = {
            println("person.encryptionKey.length: " + person.encryptionKey.length)
            person.encryptionKey.length must_== 32
        }


        def encDec = {

            println("person.encryptionKey: " + person.encryptionKey.toStr)
            println("person.encryptionKey.length : " + person.encryptionKey.length)
            cipher.init(true, new KeyParameter(person.encryptionKey))

            val data = text.getBytes("UTF-8")

            val encrypted = new Array[Byte](cipher.getOutputSize(data.length))


            var outLen = cipher.processBytes(data,0,data.length,encrypted,0)
            cipher.doFinal(encrypted, outLen)

            println("encrypted: " + encrypted)


            cipher.init(false, new KeyParameter(person.encryptionKey))

            val decrypted = new Array[Byte](cipher.getOutputSize(encrypted.length))
            println("cipher.getOutputSize(encrypted.length): " + cipher.getOutputSize(encrypted.length))
            println("decrypted.length: " + decrypted.length)
            outLen = cipher.processBytes(encrypted, 0, decrypted.length, decrypted, 0)
            println("outLen: " + outLen)
            cipher.doFinal(decrypted, outLen)

            val rv = new String(decrypted, "UTF-8")

            println("decrypted: " + rv)
            println("ori length: " + text.length)
            println("decrypted length before trim: " + rv.length)
            println("decrypted length after trim: " + rv.trim.length)

            rv.trim must_== text

        }

    }

}
