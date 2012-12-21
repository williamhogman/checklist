package checklist
package controllers

import checklist.Database.db

import com.twitter.finatra.Controller
import com.twitter.util.Future

import com.mongodb.util.JSON
import com.mongodb.{BasicDBObject, BasicDBList}
import com.mongodb.casbah.Imports.{DBObject, DBList}
import com.mongodb.casbah.commons.{MongoDBObject, MongoDBList}

import java.nio.charset.Charset

class PrototypeController extends Controller {
  get("/users/:username/prototypes") { request =>
    val username = request.routeParams("username")

    //var user = new MongoDBObject(
    // db("users").findOne(DBObject("name" -> username)).get)

    //var prototypes = user.as[MongoDBList]("prototypes")
    val templates = Templates.getUserTemplates(username).get
    render.json(templates).toFuture
  }

  post("/users/:username/prototypes") { request =>
    val username = request.routeParams("username")
    val prototype = JSON.parse(
      request.content.toString(Charset.forName("UTF-8")))

    var user = db("users").findOne(DBObject("name" -> username)).get
    db("users").update(
      user, DBObject("$push" -> DBObject("prototypes" -> prototype)))

    render.plain("Success").toFuture
  }

  get("/users/:username/prototypes/:protoId") { request =>
    val username = request.routeParams("username")
    val protoId = request.routeParams("protoId").toInt

    var user = new MongoDBObject(
      db("users").findOne(DBObject("name" -> username)).get)
    var prototypes = user.as[MongoDBList]("prototypes")

    render.json(prototypes(protoId)).toFuture
  }

  post("/users/:username/prototypes/:protoId") { request =>
    val username = request.routeParams("username")
    val protoId = request.routeParams("protoId").toInt
    val prototypeItem = JSON.parse(
      request.content.toString(Charset.forName("UTF-8")))

    val prototypeItemsKey = "prototypes." + protoId + ".items"

    db("users").update(
      DBObject("name" -> username),
      DBObject("$push" -> DBObject(prototypeItemsKey -> prototypeItem)))

    render.plain("Success").toFuture
  }

  /*put("/users/:username/prototypes/:protoId") { request =>
    val username = request.routeParams("username")
    val protoId = request.routeParams("protoId").toInt
    val updateQuery = JSON.parse(
      request.content.toString(Charset.forName("UTF-8"))).asInstanceOf[BasicDBObject]

    db("users").update(DBObject("name" -> username), updateQuery)

    render.plain("Success").toFuture
  }*/

  delete("/users/:username/prototypes/:protoId") { request =>
    val username = request.routeParams("username")
    val protoId = request.routeParams("protoId").toInt

    db("users").update(
      DBObject("name" -> username),
      DBObject("$unset" -> DBObject(
        "prototypes." + protoId -> 1)))
    db("users").update(
      DBObject("name" -> username),
      DBObject("$pull" -> DBObject("prototypes" -> null)))

    render.plain("Success").toFuture
  }

  delete("/users/:username/prototypes/:protoId/:itemId") { request =>
    val username = request.routeParams("username")
    val protoId = request.routeParams("protoId").toInt
    val itemId = request.routeParams("itemId").toInt

    val prototypeItemsKey = "prototypes." + protoId + ".items"

    db("users").update(
      DBObject("name" -> username),
      DBObject("$unset" -> DBObject(
        prototypeItemsKey + "." + itemId -> 1)))
    db("users").update(
      DBObject("name" -> username),
      DBObject("$pull" -> DBObject(prototypeItemsKey -> null)))

    render.plain("Success").toFuture
  }
}
