package checklist

import com.mongodb.casbah.Implicits._
import com.mongodb.casbah.commons.{MongoDBObject,MongoDBList}
import com.fasterxml.jackson.annotation.JsonProperty

// TODO: Get the user json thingy to work.
class User(
  @JsonProperty("username")
  var username: String,
  @JsonProperty("realname")
  var realname: String,
  var isAdmin: Boolean,
  var isProcDev: Boolean) {

  def this(obj: MongoDBObject) = this(
    username = obj.as[String]("username"),
    realname = obj.as[String]("realname"),
    isAdmin = obj.as[Boolean]("isAdmin"),
    isProcDev = obj.as[Boolean]("isProcDev")
  )

}

object Users {
  private val db = checklist.Database.db

  implicit def MongoObjToUser(o: MongoDBObject): User = new User(o)

  def byUsername(username: String): Option[User] = {
    db("users").findOne(MongoDBObject("username" -> username)).map(wrapDBObj).map(MongoObjToUser)
  }
}

