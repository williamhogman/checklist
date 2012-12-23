package checklist

import org.bson.types.ObjectId
import com.mongodb.casbah.Imports._
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

  def getUsername = username
  def getRealname = realname

}

object User extends MongoColMixin {
  private val db = checklist.Database.db

  protected def mdbcol = db("users")

  implicit def MongoObjToUser(o: MongoDBObject): User = new User(o)
  implicit def MongoObjToUser(o: Option[MongoDBObject]) : Option[User] = o map(MongoObjToUser(_))

  implicit object UserIsIdFindable extends IdFindable[User] {
    def byId(id: ObjectId) = findOneById(id) map(new User(_))
  }
  
  private def usernameQuery(username: String) : MongoDBObject =
    MongoDBObject("username" -> username)

  def byUsername(username: String): Option[User] =
    findOne(usernameQuery(username))

  def addTemplate(username: String, id: ObjectId) = update(
    usernameQuery(username),
    MongoDBObject("$addToSet" -> MongoDBObject("templates" -> id))
  )

  def removeTemplate(username: String, id: ObjectId) = update(
    usernameQuery(username),
    MongoDBObject("$pull" -> MongoDBObject("templates" -> id))
  )
    

}
