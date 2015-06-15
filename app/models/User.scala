package models

import collection.JavaConversions._
import collection.mutable.ArrayBuffer
import com.couchbase.client.CouchbaseClient
import java.net.URI
import java.net.ConnectException
import java.util.UUID


import play.api.mvc._
import play.api.libs.json._
import play.api.libs.json.util._
import play.api.libs.functional.syntax._

case class User(name: String, email: String, var id: String = "", var confirmed: Boolean = true) {
    
}

object User
{
  implicit val userImplicitWrites = Json.writes[User]

  implicit def toUser(json : JsValue) : User = {
    
    var id = (json \ "id").asOpt[String]
    var name = (json \ "name").asOpt[String]
    var email = (json \ "email").asOpt[String]
    var confirmed = (json \ "confirmed").asOpt[Boolean]

    return new User(name.getOrElse(null), email.getOrElse(null), id.getOrElse(null), confirmed.getOrElse(false))
  }

  protected def newCouchbaseClient()  = new CouchbaseClient(ArrayBuffer(URI.create("http://127.0.0.1:8091/pools")), "default", "")

}