package com.ansvia.dcsis

import org.specs2.specification.Fragments
import java.io.{File, FileOutputStream}

/**
 * Author: robin
 * Date: 4/20/14
 * Time: 6:34 PM
 *
 */
class SignSpec extends DcsisTest {
    def is: Fragments = "Sign should" ^
        p ^
        "able to generate qr code" ! trees.genQrCode ^
        p ^
        end

    object trees {

        val data = "ini data yang mau di-sign".getBytes("UTF-8")
        val p1 = genPerson

        val sign = p1.sign(data)

        def genQrCode = {
            println(sign)

            val path = "/tmp/qrcode-sign.gif"
            val fw = new FileOutputStream(path)
            fw.write(sign.qrCode.toByteArray)
            fw.close()

            println("qrcode sign = " + sign)

            new File(path).exists() must beTrue
        }

    }
}
