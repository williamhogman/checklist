package checklist
package models

import checklist.Database
import com.mongodb.casbah.commons.MongoDBObject
import com.mongodb.casbah.commons.MongoDBList

import com.mongodb.BasicDBObject

class UserModel(var dbObject: MongoDBObject) {
  var lists: List[ListModel] = List()
  for (l <- dbObject.as[MongoDBList]("lists").toList) {
    lists ::= new ListModel(new MongoDBObject(l.asInstanceOf[BasicDBObject]))
  }

  var prototypes: List[PrototypeModel] = List()
  for (p <- dbObject.as[MongoDBList]("prototypes").toList) {
    prototypes ::= new PrototypeModel(new MongoDBObject(p.asInstanceOf[BasicDBObject]))
  }

  def name = dbObject.as[String]("name")
  def name_=(value: String) {
    dbObject("name") = value
  }

  def save() {
    Database("users").save(dbObject.asDBObject)
  }
}

object UserModel {
  def apply(userName: String) = {
    var dbObject = Database("users").findOne(MongoDBObject("name" -> userName))
    new UserModel(new MongoDBObject(dbObject.get))
  }
}
