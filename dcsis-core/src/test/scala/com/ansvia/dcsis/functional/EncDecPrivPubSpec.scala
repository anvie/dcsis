package com.ansvia.dcsis.functional

/**
 * Author: robin
 * Date: 4/20/14
 * Time: 5:11 PM
 *
 */

import org.specs2.Specification
import java.security.{Security, SecureRandom, KeyPairGenerator}
import javax.crypto.Cipher

class EncDecPrivPubSpec extends Specification {

    def is = "Encryption" ^
        sequential ^
        "can encrypt using private key and decrypt using public key" ! trees.test1 ^
        "cannot decrypt non paired public key" ! trees.test2 ^
        end

    object trees {

        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider())

        val keyGen = KeyPairGenerator.getInstance("RSA")
        private val secRand = SecureRandom.getInstance("SHA1PRNG")

        keyGen.initialize(512, secRand)

        val keys = keyGen.generateKeyPair()
        val otherKeys = keyGen.generateKeyPair()

        val c = Cipher.getInstance("RSA/NONE/OAEPWithMD5AndMGF1Padding", "BC")

        var encrypted:Array[Byte] = _

        val text = "hello :)"

        def test1 = {

            val data = text.getBytes("UTF-8")

            c.init(Cipher.ENCRYPT_MODE, keys.getPrivate)
            encrypted = c.doFinal(data)

            println("encrypted: \n" + new String(encrypted, "UTF-8"))

            c.init(Cipher.DECRYPT_MODE, keys.getPublic)
            val decrypted = c.doFinal(encrypted)

            val rv = new String(decrypted, "UTF-8").trim
            println("decrypted: " + rv)

            text must_== rv
        }

        def test2 = {
            try {
                c.init(Cipher.DECRYPT_MODE, otherKeys.getPublic)
                val decrypted = c.doFinal(encrypted)

                val rv = new String(decrypted, "UTF-8").trim
                println("decrypted: " + rv)

                text mustNotEqual rv
            }
            catch {
                case e:Exception =>
                    true must beTrue
            }
        }


    }

}

