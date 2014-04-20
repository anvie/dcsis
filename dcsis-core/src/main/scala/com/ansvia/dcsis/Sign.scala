package com.ansvia.dcsis

import com.ansvia.dcsis.helpers.HexHelpers._
import net.glxn.qrgen.image.ImageType
import net.glxn.qrgen.QRCode


/**
 * Author: robin
 * Date: 4/20/14
 * Time: 5:58 PM
 *
 */
case class Sign(bytes:Array[Byte], person:Person){
    lazy val toStr = bytes.toStr

    override def toString = "Sign(" + toStr + ")"

    def getQrCode(url:String) = {
        val data = Sign.VERSION + "\n" + "sg\n" + person.id + "\n" +
            url + "\n" +
            bytes.toStr

        println("data: " + data)
        QRCode.from(data).withSize(200,200)
            .to(ImageType.GIF)
    }
}

object Sign {
    val VERSION = 1
}