package controllers

import play.api.mvc._
import play.api.libs.json._
import models._
import collection.JavaConversions._
import collection.mutable.ArrayBuffer
import java.net.URI
import java.net.ConnectException
import com.couchbase.client.CouchbaseClient
import javax.inject.{ Singleton, Inject }
import services._
import com.typesafe.plugin._
import play.api.Play.current
import play.mvc.Http

@Singleton
class Application @Inject() (subscriptionService: SubscriptionService) extends Controller {

  def index = Action { request =>
    {
      if(request.host.startsWith("104."))
        Redirect("http://consultores.triphunter.com.br/", 301)
      else
        Ok(views.html.index())
    }
  }

  def survey(userId: String) = Action {

    val user = subscriptionService.getUser(userId)

    if (user == null)
      NotFound("Cadastro não encontrado")
    else {
      user.confirmed = true;
      subscriptionService.save(user)
      Ok(views.html.survey(user))
    }

  }
}