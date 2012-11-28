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

class ListController extends Controller {
  get("/users/:username/lists") { request =>
    val username = request.routeParams("username")

    var user = new MongoDBObject(
      db("users").findOne(DBObject("name" -> username)).get)
    var lists = user.as[MongoDBList]("lists")

    render.json(lists).toFuture
  }

  post("/users/:username/lists") { request =>
    val username = request.routeParams("username")
    val list = JSON.parse(
      request.content.toString(Charset.forName("UTF-8")))

    var user = db("users").findOne(DBObject("name" -> username)).get
    db("users").update(
      user, DBObject("$push" -> DBObject("lists" -> list)))

    render.plain("Success").toFuture
  }

  get("/users/:username/lists/:listId") { request =>
    val username = request.routeParams("username")
    val listId = request.routeParams("listId").toInt

    var user = new MongoDBObject(
      db("users").findOne(DBObject("name" -> username)).get)
    var lists = user.as[MongoDBList]("lists")

    render.json(lists(listId)).toFuture
  }

  post("/users/:username/lists/:listId") { request =>
    val username = request.routeParams("username")
    val listId = request.routeParams("listId").toInt
    val listItem = JSON.parse(
      request.content.toString(Charset.forName("UTF-8")))

    val listItemsKey = "lists." + listId + ".items"

    db("users").update(
      DBObject("name" -> username),
      DBObject("$push" -> DBObject(listItemsKey -> listItem)))

    render.plain("Success").toFuture
  }

  /*put("/users/:username/lists/:listId") { request =>
    render.plain("Not implemented").toFuture
  }*/

  delete("/users/:username/lists/:listId") { request =>
    val username = request.routeParams("username")
    val listId = request.routeParams("listId").toInt

    db("users").update(
      DBObject("name" -> username),
      DBObject("$unset" -> DBObject(
        "lists." + listId -> 1)))
    db("users").update(
      DBObject("name" -> username),
      DBObject("$pull" -> DBObject("lists" -> null)))

    render.plain("Success").toFuture
  }

  delete("/users/:username/lists/:listId/:itemId") { request =>
    val username = request.routeParams("username")
    val listId = request.routeParams("listId").toInt
    val itemId = request.routeParams("itemId").toInt

    val listItemsKey = "lists." + listId + ".items"

    db("users").update(
      DBObject("name" -> username),
      DBObject("$unset" -> DBObject(
        listItemsKey + "." + itemId -> 1)))
    db("users").update(
      DBObject("name" -> username),
      DBObject("$pull" -> DBObject(listItemsKey -> null)))

    render.plain("Success").toFuture
  }
}
