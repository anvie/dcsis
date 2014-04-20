package com.ansvia.dcsis

import com.ansvia.dcsis.generator.PersonIdentityFactory
import org.specs2.Specification
import com.ansvia.util.idgen.TokenIdGenerator

/**
 * Author: robin
 * Date: 4/20/14
 * Time: 6:03 PM
 *
 */
trait DcsisTest extends Specification {

    object tokenGen extends TokenIdGenerator

    val fp = Array[Byte](1,2,3,4,5)
    val id = Array[Byte](6,7,8,9,10)

    val person = PersonIdentityFactory.build(fp, id, 1)

    def genPerson = {
        val fp = tokenGen.nextId().getBytes("UTF-8")
        val id = tokenGen.nextId().getBytes("UTF-8")
        PersonIdentityFactory.build(fp, id, 1)
    }
}
