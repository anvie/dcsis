package com.ansvia.dcsis.generator

import java.security._
import java.nio.{ByteOrder, ByteBuffer}
import org.bouncycastle.crypto.digests.RIPEMD256Digest
import com.ansvia.dcsis.helpers.HexHelpers._

/**
 * Author: robin
 * Date: 4/19/14
 * Time: 3:26 PM
 *
 */
object IdentityFactory {

    def build(data:Array[Byte], version:Int, salt:Array[Byte]) = {
        val md = MessageDigest.getInstance("SHA-1")

        val verBytes = ByteBuffer.allocate(4).putInt(version).order(ByteOrder.LITTLE_ENDIAN).array()

        val checkshum = md.digest(data).toStr.substring(0,5)

        val r1 = md.digest(verBytes ++ data ++ checkshum.getBytes("UTF-8"))
        val r2 = md.digest(r1 ++ salt).toStr

        r2
    }





}


case class Person(id:String, keys:KeyPair){

    lazy val encryptionKey = {
        val ripemd = new RIPEMD256Digest()
        val data = keys.getPublic.getEncoded
        ripemd.update(data, 0, data.length)
        val rv = new Array[Byte](ripemd.getDigestSize)
        ripemd.doFinal(rv, 0)
        rv
    }

}

object PersonIdentityFactory {

    private val PEOPLE_SALT = "B1BE86B51FFB2EA7EFC3730F6FF07EE4725C506A".getBytes

    def build(fingerprintData:Array[Byte], irisData:Array[Byte], version:Int) = {
        val md = MessageDigest.getInstance("SHA-1")

        val fpHash = md.digest(fingerprintData)
        val irisHash = md.digest(irisData)

        val bioMetricHash = md.digest(fpHash ++ irisHash)

        val keys = KeyGenerator.generate()

        val id = IdentityFactory.build(bioMetricHash, version, PEOPLE_SALT)

        Person(id, keys)
    }

}

case class Entity(id:String, keys:KeyPair)

object EntityIdentityFactory {

    private val ENTITY_SALT = "F6340143AA84C9061C81AF26115D3CCE1FA2F4AA".getBytes


    def build(version:Int) = {
        val md = MessageDigest.getInstance("SHA-1")

        val keys = KeyGenerator.generate()

        val keysHash = md.digest(md.digest(keys.getPublic.getEncoded))

        val id = IdentityFactory.build(keysHash, version, ENTITY_SALT)
        Entity(id, keys)
    }

}


object KeyGenerator {


    private val keyGen = KeyPairGenerator.getInstance("EC")
    private val secRand = SecureRandom.getInstance("SHA1PRNG")

    keyGen.initialize(256, secRand)

    def generate() = {
        keyGen.generateKeyPair()
    }
    

}

