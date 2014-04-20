package com.ansvia.dcsis.functional

import org.specs2.Specification
import com.ansvia.dcsis.generator.PersonIdentityFactory
import java.io.{File, FileOutputStream, FileInputStream, FileWriter}

/**
 * Author: robin
 * Date: 4/20/14
 * Time: 3:56 PM
 *
 */
class PersonQRCodeSpec extends Specification {

    def is = "Person QR code" ^
        p ^
        "generate id QR code" ! trees.genIdQrCode ^
        p ^
        end

    object trees {

        val fp = Array[Byte](1,2,3,4,5)
        val id = Array[Byte](6,7,8,9,10)

        val person = PersonIdentityFactory.build(fp, id, 1)

        def genIdQrCode = {
            val bos = person.idQrCode

            val path = "/tmp/qrcode-" + person.id + ".gif"
            val fw = new FileOutputStream(path)
            fw.write(bos.toByteArray)
            fw.close()

            println("QR code output: " + path)
            new File(path).exists() must beTrue
        }

    }

}
