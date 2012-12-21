package checklist

import com.mongodb.casbah.Implicits._
import com.mongodb.casbah.commons.{MongoDBObject,MongoDBList}

object Templates {
  private val db = checklist.Database.db


  def getUserTemplates(username: String) = {
    val fields = MongoDBObject("prototypes" -> 1, "_id" -> 0)
    db("users").findOne(MongoDBObject("username" -> username), fields) map {
      _.as[MongoDBList]("prototypes") map {_.toString()}
    }
  }
}
