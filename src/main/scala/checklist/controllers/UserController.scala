package checklist
package controllers

import com.twitter.finatra.Controller
import com.twitter.util.Future

import checklist.DBClient._
//import checklist.models.UserModel


class UserController extends Controller {
  post("/users") { request =>
    render.plain("Not implemented").toFuture
  }

  get("/users/:username") { request =>
    val username = request.routeParams("username")
    render.json(User.byUsername(username)).toFuture
  }

  put("/users/:username") { request =>
    render.plain("Not implemented").toFuture
  }

  delete("/users/:username") { request =>
    render.plain("Not implemented").toFuture
  }

  get("/userid/:userid") {
    request =>
    val uid = request.routeParams("userid")
    val resp = DBClient.byId[User](uid) match {
      case Some(x) => render.json(x)
      case None => render.plain("not found").notFound
    }
    resp.toFuture
  }
}
