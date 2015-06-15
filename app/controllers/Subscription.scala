package controllers

import models._
import play.api.libs.json._
import play.api.mvc._
import views.html.defaultpages.badRequest
import views.html.defaultpages.notFound
import repositories.{ UserRepository, SurveyRepository }
import javax.inject.{ Singleton, Inject }
import services._

@Singleton
class Subscription @Inject() (subscriptionService: SubscriptionService, mailer: MailerService) extends Controller {

  def submitUser() = Action(parse.tolerantJson) { request =>
    val user: User = Json.parse(request.body.toString())
    if (subscriptionService.save(user)) {
      val emailHtml = views.html.email(request.host, user).body
      if(mailer.send(user.name, user.email, emailHtml))
        Ok("E-mail registrado com sucesso!")
      else
        BadRequest("Ocorreu um erro inesperado.")
    } else
      BadRequest("Todos os campos são obrigatórios e o e-mail fornecido precisa ser válido.")
  }

  def getUser(id: String) = Action {

    val user: User = subscriptionService.getUser(id)

    if (user == null)
      NotFound("ID Not Found: " + id)
    else
      Ok(Json.toJson(user))
  }

  def getSurvey(userId: String) = Action {
    val survey: Survey = subscriptionService.getSurvey(userId)
    if (survey == null)
      NotFound("Questionário não encontrado.")
    else
      Ok(Json.toJson(survey))
  }

  def submitSurvey(id: String) = Action(parse.tolerantJson) { request =>
    val survey: Survey = Json.parse(request.body.toString())
    if (subscriptionService.save(id, survey))
      Ok("Questionário enviado com sucesso!")
    else
      BadRequest("Erro desconhecido")
  }

}