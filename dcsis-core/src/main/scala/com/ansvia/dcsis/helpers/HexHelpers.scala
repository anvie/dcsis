package com.ansvia.dcsis.helpers

/**
 * Author: robin
 * Date: 4/20/14
 * Time: 2:46 PM
 *
 */
object HexHelpers {

    class BytesWrapper(bytes:Array[Byte]){
        def toStr = {
            bytes.map("%02x".format(_)).mkString
        }
    }
    implicit def toBytesWrapper(bytes:Array[Byte]) = new BytesWrapper(bytes)

}
