package repositories

import models._
import collection.JavaConversions._
import collection.mutable.ArrayBuffer

import com.couchbase.client._
import java.net.URI
import java.net.ConnectException

import java.util.UUID

import play.api.mvc._
import play.api.libs.json._
import play.api.libs.json.util._
import play.api.libs.functional.syntax._

trait UserRepository {
  def getById(id: String): models.User

  def insert(user: models.User): Boolean

}

class CouchbaseUserRepository extends UserRepository {

  def getById(id: String): User = {
    val client = newCouchbaseClient()
    val userJson = client.get(id)
    client.shutdown()

    var ret: User = null
    println("userJson:")
    if (userJson != null) {
      ret = Json.parse(userJson.toString())
      println(userJson.toString())
    }

    return ret
  }

  def insert(user: User): Boolean = {

    val client = newCouchbaseClient()
    if (user.id == null)
      user.id = UUID.randomUUID().toString()
    val userJson = Json.toJson(user).toString()
    val result = client.set(user.id, 0, userJson).get()
    client.shutdown()

    return true
  }

  protected def newCouchbaseClient() = new CouchbaseClient(ArrayBuffer(URI.create("http://127.0.0.1:8091/pools")), "default", "")

}