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
case class Sign(bytes:Array[Byte]){
    lazy val toStr = bytes.toStr

    override def toString = "Sign(" + toStr + ")"

    lazy val qrCode = QRCode.from(toStr).to(ImageType.GIF).stream()
}
