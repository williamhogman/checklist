package checklist

import com.mongodb.casbah.Imports._

import org.bson.types.ObjectId

object Database {
  val connection = MongoConnection()
  val db = connection("checklist_test")
}

trait DBRefable[T] {
  def ObjectId(): ObjectId
  def deref(): Option[T]
}

class DBRef[T: IdFindable](val id: ObjectId) extends DBRefable[T] {
  def ObjectId() = id
  def deref() = {
    val findable = implicitly[IdFindable[T]]
    findable.byId(id)  
  }
}

trait MongoColMixin {
  protected def mdbcol : MongoCollection

  protected def find(query: MongoDBObject): Iterator[MongoDBObject] =
    mdbcol.find(query) map(wrapDBObj)

  protected def findOne(query: MongoDBObject) : Option[MongoDBObject] =
    mdbcol.findOne(query) map(wrapDBObj)

  protected def findOneById(id: ObjectId) =
    mdbcol.findOneByID(id)

  protected def update(query: MongoDBObject, to: MongoDBObject) =
    mdbcol.update(query, to)

}

trait IdFindable[T] {
  def byId(id: ObjectId): Option[T]
}

trait DBClientImp {
  def byId[T: IdFindable](id: ObjectId) = {
    val findable = implicitly[IdFindable[T]]
    findable.byId(id)
  }

  def byId[T: IdFindable](id: String): Option[T] =
    makeObjectId(id) flatMap(byId[T](_))

  private def makeObjectId(id: String): Option[ObjectId] =
    if (ObjectId.isValid(id)) Some(new ObjectId(id))
    else None


}

trait DBImplicits //extends TemplateImplicits with UserImplicits

object DBClient extends DBClientImp
