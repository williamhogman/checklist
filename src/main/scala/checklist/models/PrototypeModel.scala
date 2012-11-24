package checklist
package models

import checklist.Database
import com.mongodb.casbah.commons.MongoDBObject
import com.mongodb.casbah.commons.MongoDBList

import com.mongodb.BasicDBObject

import com.mongodb.util.JSON

class PrototypeItemModel(var dbObject: MongoDBObject) {
  def text = dbObject.as[String]("text")
  def text_=(value: String) {
    dbObject("text") = value
  }

  def checked = dbObject.as[Boolean]("checked")
  def checked_=(value: Boolean) {
    dbObject("checked") = java.lang.Boolean.valueOf(value)
  }
}

class PrototypeModel(var dbObject: MongoDBObject) {
  var items: List[PrototypeItemModel] = List()
  for (i <- dbObject.as[MongoDBList]("items").toList) {
    items ::= new PrototypeItemModel(new MongoDBObject(i.asInstanceOf[BasicDBObject]))
  }

  def name = dbObject.as[String]("name")
  def name_=(value: String) {
    dbObject("name") = value
  }
}
