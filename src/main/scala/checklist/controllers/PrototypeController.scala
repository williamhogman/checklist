package checklist
package controllers

import checklist.Database.db

import com.twitter.finatra.Controller
import com.twitter.util.Future

import com.codahale.jerkson.Json

import com.mongodb.util.JSON
import com.mongodb.{BasicDBObject, BasicDBList}
import com.mongodb.casbah.Imports.{DBObject, DBList}
import com.mongodb.casbah.commons.{MongoDBObject, MongoDBList}

import java.nio.charset.Charset

class PrototypeController extends Controller {
  get("/users/:username/prototypes") { request =>
    val username = request.routeParams("username")
    Template.getUserTemplates(username) match {
      case Some(template) => render.json(template).toFuture
      case None => render.notFound.plain("user not found").toFuture
    } 
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

  get("/users/:username/prototypes/:id") { 
    request =>
    val username = request.routeParams("username")
    val id = request.routeParams("id")
    Template.byId(id) match {
      case Some(value) => render.json(value).toFuture
      case None => render.plain("not found").toFuture
    }
  }

  post("/users/:username/prototypes/:id") {
    request =>
    val username = request.routeParams("username")
    val id = request.routeParams("id")

    val item = request.withReader(Json.parse[TemplateItem])

    Template.addItem(id, item) match {
      case Some(x) => render.json(item).toFuture
      case None => render.notFound.plain("not found").toFuture
    }
  }

  delete("/users/:username/prototypes/:id") {
    request =>
    val username = request.routeParams("username")
    val id = request.routeParams("id")

    Template.delete(id) match {
      case Some(x) => render.plain("deleted").toFuture
      case None => render.notFound.plain("template not found").toFuture
    }
  }

  delete("/users/:username/prototypes/:templateid/:itemid") {
    request =>
    val username = request.routeParams("username")
    val template = request.routeParams("templateid")
    val itemid = request.routeParams("itemid").toInt

    Template.deleteItem(template, itemid)
    render.plain("deleted").status(204).toFuture
  }
}
