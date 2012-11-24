package checklist

import com.mongodb.casbah.MongoConnection

object Database {
  val connection = MongoConnection()
  val db = connection("checklist_test")
}
