package com.ansvia.dcsis


import com.ansvia.dcsis.helpers.HashHelper._

/**
 * Author: robin
 * Date: 4/21/14
 * Time: 3:12 PM
 *
 */

abstract class Article {

    def hash:String
    def owner:BaseIdentity

}

case class TextualArticle(id:Long, content:String, ownerId:String, sign:Sign) extends Article {

    def hash = content.sha1

    // @TODO(robin): code here
    def owner: BaseIdentity = null
}

case class RefArticle(id:Long, url:String, ownerId:String, sign:Sign) extends Article {

    def hash = url.sha1

    // @TODO(robin): code here
    def owner: BaseIdentity = null

    // @TODO(robin): code here
    def getRef:Article = null
}
