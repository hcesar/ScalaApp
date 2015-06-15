package controllers

import models._
import services._
import play.api.libs.json._
import play.api.mvc._
import play.mvc.Http
import play.api.Play.current
import javax.inject.{ Singleton, Inject }

/**
 * @author Henrique
 */

@Singleton
class Report @Inject() (svc: SubscriptionService) extends Controller {
  def getSurveys() = Action { implicit request =>

    val reportUser = current.configuration.getString("app.reportUser").getOrElse(null)
    val reportPassword = current.configuration.getString("app.reportPassword").getOrElse(null)

    val (user, pass) = decodeBasicAuth(request.headers.get("Authorization"))

    if (user == reportUser && pass == reportPassword) {

      val csv = getCsv()

      Ok(csv)
          .withHeaders(
                (Http.HeaderNames.CONTENT_TYPE -> "text/csv"), 
                ("Content-disposition" -> "attachment; filename=report.csv"))
    } else {
      Unauthorized("Não autorizado.").withHeaders((Http.HeaderNames.WWW_AUTHENTICATE -> "Basic Realm=\"TripHunter\""))
    }
  }

  private def getCsv(): String = {
    val sb = new StringBuilder()
    val surveys = svc.listSurveys()
    sb.append("userId;name;email;city;state;isCertified;email;occupation;tripKinds;regions;message")
    sb.append("\r\n")
    for (survey <- surveys) {
      sb.append(s"\042${survey.userId}\042;\042${survey.name}\042;\042${survey.email}\042;\042${survey.city}\042;\042${survey.state}\042;\042${survey.isCertified}\042;\042${survey.email}\042;\042${survey.occupation}\042;\042${survey.tripKinds}\042;\042${survey.regions}\042;\042${survey.message}\042")
      sb.append("\r\n")
    }

    return sb.toString()
  }

  private def decodeBasicAuth(auth: Option[String]) = {

    var authToken = " : "

    if (auth != None) {
      val baStr = auth.getOrElse(null).replaceFirst("Basic ", "")
      authToken = new String(new sun.misc.BASE64Decoder().decodeBuffer(baStr), "UTF-8")
    }

    val Array(user, pass) = authToken.split(":")
    (user.trim(), pass.trim)
  }
}