package com.ansvia.dcsis

import com.ansvia.commons.logging.Slf4jLogger
import java.net.{InetSocketAddress, InetAddress}
import com.jolira.gossip.{Listener, Service}

/**
 * Author: robin
 * Date: 4/21/14
 * Time: 9:27 AM
 *
 */
object Main extends Slf4jLogger {

    def main(args: Array[String]) {
        
        val host = InetAddress.getByName(args(0))
        val port = args(1).toInt
        val seedsStr = if (args.length > 2){
            args(2)
        }else{
            ""
        }

        println("binding to " + host + ":" + port)
        println("seeds: " + seedsStr.split(",").toList)

        val seeds = seedsStr.split(",").filter(_.contains(":")).map { s =>
            val sl = s.split(":")
            val (_h,_p) = (sl(0), sl(1).toInt)
            new InetSocketAddress(_h, _p)
        }

        println(seeds)

        val addr = new InetSocketAddress(host, port)
        val svc = new Service(addr, Array(addr) ++ seeds)

        svc.add("topic.", new Listener(){
            override def handleMessage(topic: String, message: String){
                println("#" + topic + ": " + message)
            }
        })

        while (true){
            Thread.sleep(5000)
            svc.send("topic1","hello from " + host + ":" + port)
            print(".")
        }
    }
}
