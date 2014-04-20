package com.ansvia.dcsis

import java.security.{Signature, KeyPair}
import org.bouncycastle.crypto.digests.RIPEMD256Digest
import net.glxn.qrgen.QRCode
import net.glxn.qrgen.image.ImageType
import org.bouncycastle.util.encoders.Base64
import com.ansvia.dcsis.generator.KeyGenerator

/**
 * Author: robin
 * Date: 4/20/14
 * Time: 8:36 PM
 *
 */
case class Person(id:String, keys:KeyPair){

    lazy val encryptionKey = {
        val ripemd = new RIPEMD256Digest()
        val data = keys.getPublic.getEncoded
        ripemd.update(data, 0, data.length)
        val rv = new Array[Byte](ripemd.getDigestSize)
        ripemd.doFinal(rv, 0)
        rv
    }

    lazy val idQrCode = {
        QRCode.from(id).to(ImageType.GIF)
    }

    lazy val pubKeyQrCode = {
        val bkey = new String(Base64.encode(keys.getPublic.getEncoded)).trim
        println("pubKey b64: " + bkey + ". len: " + bkey.length)
        QRCode.from(bkey).to(ImageType.GIF).stream()
    }


    def sign(data:Array[Byte]) = {
        val signer = Signature.getInstance(Config.signAlgo, "BC")
        signer.initSign(keys.getPrivate, KeyGenerator.secRand)
        signer.update(data)
        Sign(signer.sign(), this)
    }


}
