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

}

class Template(
  var requireSignoff: Boolean,
  var description: String,
  var items: Seq[TemplateItem]) {

  def this(obj: MongoDBObject) = this(
    requireSignoff = obj.as[Boolean]("requireSignoff"),
    description = obj.as[String]("description"),
    items = obj.as[MongoDBList]("items").map(_.asInstanceOf[MongoDBObject]).map(new TemplateItem(_))
  )
}

object Templates {
  private val db = checklist.Database.db

  private def makeObjectId(id: String): Option[ObjectId] =
    if (ObjectId.isValid(id)) Some(new ObjectId(id))
    else None

  def byId(id: String): Option[Template] = {
    val col = db("templates")
    val query = makeObjectId(id) map { x => MongoDBObject("_id" -> x) }
    query map(col.findOne(_)) flatMap {x => x.map(new Template(_))}
  }

  def getUserTemplates(username: String) = {
    val fields = MongoDBObject("prototypes" -> 1, "_id" -> 0)
    db("users").findOne(MongoDBObject("username" -> username), fields) map {
      _.as[MongoDBList]("prototypes") map {_.toString()}
    }
  }

  def addItem(id: String, item: TemplateItem) = {
    val col = db("templates")
    val upd = MongoDBObject("$push" -> ("items" -> item))
    makeObjectId(id) map { x=> col.update(MongoDBObject("_id" -> x), upd) }
  }

}
