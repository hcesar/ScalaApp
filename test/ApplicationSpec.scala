import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._
import play.api.test._
import play.api.test.Helpers._
import mocks._
import models._
import play.test.FakeApplication
import services._

@RunWith(classOf[JUnitRunner])
class ApplicationSpec extends Specification {
  val svc = new SubscriptionServiceDefault(new UserRepositoryMock(), new SurveyRepositoryMock())

  val user = new User(id = "id", name = "João", email = "joão@gmail.com" )
  val survey = new Survey(userId = "id", message = "message")
  
  "Subscription Controller" should{
    "return user When Id is present in repository" in{
      var svc = getService();
      svc.save(user) must beTrue
      svc.getUser("id") must not beNull
    }
    
    "return null When Id is not present in repository" in{
      var svc = getService();
      svc.getUser("id") must beNull
    }
    
     "return true When inserting survey for existing user" in{
      var svc = getService();
      svc.save(user) must beTrue
      svc.save(user.id, survey) must beTrue
    }
     
    "return false When inserting survey for non existing user" in{
      var svc = getService();
      svc.save(user) must beTrue
      svc.save("id2", survey) must beFalse
    }
  }
  
  def getService() : SubscriptionService ={
    return new SubscriptionServiceDefault(new UserRepositoryMock(), new SurveyRepositoryMock())
  }
  
}
