package mocks

import repositories._
import models._
import scala.collection.mutable.ArrayBuffer

class UserRepositoryMock extends UserRepository{
   val items = new ArrayBuffer[models.User]()
  
   def getById(id : String) : models.User = {
     items.find(item => item.id == id).getOrElse(null)
   }
   
   def insert(user : models.User) : Boolean = {
     items += user
     return true
   }   
}



class SurveyRepositoryMock extends SurveyRepository{
   val items = new ArrayBuffer[models.Survey]()
  
   def getByUser(userId : String) : models.Survey = {
     items.find(item => item.userId == userId).getOrElse(null)
   }
   
   def insert(userId: String, survey : Survey) : Boolean = {
     items += survey
     return true
   }   
}