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

trait SurveyRepository {
  def getByUser(userId: String): Survey

  def insert(userId: String, survey: Survey): Boolean

  def listAll():Seq[Survey]

}

class CouchbaseSurveyRepository extends SurveyRepository {

  def getByUser(userId: String): Survey = {
    val client = newCouchbaseClient()
    val responseJson = client.get("survey." + userId)
    client.shutdown()

    if (responseJson != null)
      return Survey.fromJson(responseJson.toString())
    return null
  }

  def insert(userId: String, survey: Survey): Boolean = {
    val client = newCouchbaseClient()
    survey.userId = userId
    val formJson = Json.toJson(survey).toString()
    val result = client.set("survey." + userId, 0, formJson).get()
    client.shutdown()
    return true
  }

  def listAll() :Seq[Survey]= {

    val client = newCouchbaseClient()
    val view = client.asyncGetView("dev_doc", "surveys").get(30, java.util.concurrent.TimeUnit.SECONDS)
    val query = new com.couchbase.client.protocol.views.Query()
    query.setIncludeDocs(true)
    val response = client.query(view, query)
    val total = response.getTotalRows().toInt
    val it = response.iterator()
    client.shutdown()
    var i = 0;
    
    for (i <- 1 to total)
      yield Survey.fromJson(it.next().getDocument().toString())
  }

  protected def newCouchbaseClient() = new CouchbaseClient(ArrayBuffer(URI.create("http://127.0.0.1:8091/pools")), "default", "")

}