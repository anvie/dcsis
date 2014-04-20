package com.ansvia.dcsis.functional

/**
 * Author: robin
 * Date: 4/20/14
 * Time: 6:01 PM
 *
 */

import com.ansvia.dcsis.DcsisTest
import com.ansvia.dcsis.verifier.SignatureVerifier
import java.io.StringWriter
import org.apache.commons.io.IOUtils

class SignVerifySpec extends DcsisTest {

    def is = "Signature verifier should" ^
        sequential ^
        "can verify using correct key" ! trees.canVerify ^
        "can't verify using other person key" ! trees.invalid ^
        end

    object trees {

        val person1 = genPerson
        val person2 = genPerson

        val text = {
            val sw = new StringWriter()
            val s = getClass.getClassLoader.getResourceAsStream("lorem.txt")
            IOUtils.copy(s, sw)
            sw.toString
        }
        println(text)
        val data = text.getBytes("UTF-8")

        val sign = person1.sign(data)

        println("sign: " + sign)

        def canVerify = {
            SignatureVerifier.verify(sign, person1.keys.getPublic, data) must beTrue
        }
        
        def invalid = {
            SignatureVerifier.verify(sign, person2.keys.getPublic, data) must beFalse
        }


    }

}

