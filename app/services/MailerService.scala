package services

import models.User
import com.typesafe.plugin._
import play.api.Play.current

trait MailerService {
  def send(recipient: String, recipienteEmail: String, emailHtml: String)
}

/**
 * @author Henrique
 */
class MailerServiceDefault() extends MailerService {
  def send(recipientName: String, recipienteEmail: String, emailHtml: String) = {

    val mail = use[MailerPlugin].email
    mail.setFrom("TripHunter | Curadores de Experiência <fhenrique@gmail.com>")
    mail.setRecipient(recipientName + " <" + recipienteEmail + ">")
    mail.setBcc(List("fhenrique@gmail.com"): _*)
    mail.setSubject("Bem-vindo ao TripHunter")
    mail.sendHtml(emailHtml)
  }
}