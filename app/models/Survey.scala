package models

import java.util.UUID
import play.api.mvc._
import play.api.libs.json._
import play.api.libs.json._
import play.api.libs.json.util._
import play.api.libs.functional.syntax._

/**
 * @author Henrique
 */
case class Survey(var userId: String, var name: String = null, var email: String = null, var city: String = null,
                  var state: String = null, var isCertified: Boolean = false, var occupation: String = null,
                  var tripKinds: String = null, var regions: String = null, var message: String = null) {

}

object Survey {
  implicit val formImplicitWrites = Json.writes[Survey]

  implicit def toSurvey(json: JsValue): Survey = {
    val userId = toStr(json, "userId")
    val name = toStr(json, "name")
    val email = toStr(json, "email")
    val city = toStr(json, "city")
    val state = toStr(json, "state")
    val isCertified = (json \ "isCertified").asOpt[Boolean].getOrElse(false)
    val occupation = toStr(json, "occupation")
    val tripKinds = toStr(json, "tripKinds")
    val regions = toStr(json, "regions")
    val message = toStr(json, "message")

    return new Survey(userId, name, email, city, state, isCertified, occupation, tripKinds, regions, message)
  }

  private def toStr(json: JsValue, property: String): String = (json \ property).asOpt[String].getOrElse(null)

  def fromJson(jsonText: String): Survey = {
    if (jsonText == null)
      return null

    val jsonValue = Json.parse(jsonText.toString())
    return toSurvey(jsonValue)
  }

}
