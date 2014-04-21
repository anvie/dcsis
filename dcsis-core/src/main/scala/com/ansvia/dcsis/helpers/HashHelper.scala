package com.ansvia.dcsis.helpers

import java.security.MessageDigest
import com.ansvia.dcsis.helpers.HexHelpers._

/**
 * Author: robin
 * Date: 4/21/14
 * Time: 3:47 PM
 *
 */
trait HashHelper {

    class StringHashable(str:String){
        val md = MessageDigest.getInstance("SHA-1")

        def sha1 = {
            md.update(str.getBytes("UTF-8"))
            val rv = md.digest()
            rv.toStr
        }
    }

    implicit def toStrHashable(str:String) = new StringHashable(str)

}

object HashHelper extends HashHelper
