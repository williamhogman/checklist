package checklist

import org.bson.types.ObjectId
import com.mongodb.casbah.Imports._
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.Date

class Signoff(
  var time: Date,
  var by: DBRefable[User]
) {

  def this(obj: MongoDBObject) = this(
    time = obj.as[Date]("time"),
    by = new DBRef[User](obj.as[ObjectId]("by"))
  )

  def getBy() = by.ObjectId.toString
  def getTime() = time
}

object Signoff extends MongoColMixin {
  private val db = checklist.Database.db
  protected def mdbcol = db("signoffs")


  implicit def MongoObjToSignoff(o: MongoDBObject): Signoff = new Signoff(o)
  implicit def MongoObjToSignoff(o: Option[MongoDBObject]) : Option[Signoff] =
    o map(MongoObjToSignoff(_))
  implicit def MongoObjToSignoff(o: Iterator[MongoDBObject]): Iterator[Signoff] =
    o map(MongoObjToSignoff(_))


  implicit object SignoffIsIdFindable extends IdFindable[Signoff] {
    def byId(id: ObjectId) = findOneById(id) map(new Signoff(_))
  }

  private def byQuery(userid: ObjectId) =
    MongoDBObject("by" -> userid)

  def byUser(user: DBRefable[User]): Iterator[Signoff] =
    find(byQuery(user.ObjectId))

  def byUser(user: ObjectId) = byUser(new DBRef[User](user))
}
