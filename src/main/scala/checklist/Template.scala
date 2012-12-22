package checklist

import scala.collection.mutable

import com.mongodb.casbah.Implicits._
import org.bson.types.ObjectId
import com.mongodb.casbah.Imports._
import com.mongodb.casbah.commons.{MongoDBObject,MongoDBList}

import com.fasterxml.jackson.annotation.JsonProperty

class TemplateItem(
  @JsonProperty("title")
  var title: String) {

  def this(obj: MongoDBObject) = this(
    title = obj.as[String]("title")
  )

  def getTitle = title

  def toMongoObject = MongoDBObject(
    "title" -> title
  )

}

class Template(
  var requireSignoff: Boolean,
  var description: String,
  var items: Seq[TemplateItem]) {

  def this(obj: MongoDBObject) = this(
    requireSignoff = obj.as[Boolean]("requireSignoff"),
    description = obj.as[String]("description"),
    items = obj.as[MongoDBList]("items").map(_.asInstanceOf[DBObject]).map(new TemplateItem(_))
  )

  def toMongoObject = MongoDBObject(
    "requireSignoff" -> requireSignoff,
    "description" -> description,
    "items" -> items.map(_.toMongoObject)
  )

  def getItems = items
  def getDescription = description
  def getRequireSignoff = requireSignoff
}

object Templates {
  private val db = checklist.Database.db

  private def makeObjectId(id: String): Option[ObjectId] =
    if (ObjectId.isValid(id)) Some(new ObjectId(id))
    else None

  private def objectIdQuery(id: ObjectId): MongoDBObject =
    MongoDBObject("_id" -> id)

  private def objectIdQuery(id: String): Option[MongoDBObject] =
    makeObjectId(id).map(objectIdQuery(_))
    

  def byId(id: String): Option[Template] = {
    val col = db("templates")
    objectIdQuery(id) map(col.findOne(_)) flatMap {x => x map(new Template(_))}
  }

  def getUserTemplates(username: String) = {
    val fields = MongoDBObject("prototypes" -> 1, "_id" -> 0)
    db("users").findOne(MongoDBObject("username" -> username), fields) map {
      _.as[MongoDBList]("prototypes") map {_.toString()}
    }
  }

  def addItem(id: String, item: TemplateItem) = {
    val col = db("templates")
    val upd = MongoDBObject("$push" -> MongoDBObject("items" -> item.toMongoObject))
    objectIdQuery(id) map(col.update(_, upd))
  }

  def delete(id: String) = {
    val col = db("templates")
    objectIdQuery(id) map(col.remove(_))
  }


  def deleteItem(id: String, itemid: Int) = {
    val col = db("templates")
    val upd = MongoDBObject("$pull" -> MongoDBObject("items" -> MongoDBObject("id" -> itemid)))
    objectIdQuery(id) map(col.update(_, upd))
  }
}
