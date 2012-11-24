package checklist

import com.mongodb.casbah.MongoConnection

object Database {
  val mongoConn = MongoConnection()
  val mongoDB = mongoConn("checklist_test")

  def conn = mongoConn

  def db = mongoDB

  def apply(collection: String) = mongoDB(collection)
}
