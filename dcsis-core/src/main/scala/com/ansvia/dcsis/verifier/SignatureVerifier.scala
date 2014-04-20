package com.ansvia.dcsis.verifier

import com.ansvia.dcsis.Sign
import java.security.{PublicKey, Signature}

/**
 * Author: robin
 * Date: 4/20/14
 * Time: 5:57 PM
 *
 */
object SignatureVerifier {

    def verify(sign:Sign, pubKey:PublicKey, data:Array[Byte]) = {
        val signer = Signature.getInstance("SHA1withECDSA", "BC")
        signer.initVerify(pubKey)
        signer.update(data)
        signer.verify(sign.bytes)
    }

}
