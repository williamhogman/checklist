package checklist
package controllers

import com.twitter.finatra.Controller
import com.twitter.util.Future

//import checklist.models.UserModel


class UserController extends Controller {
  post("/users") { request =>
    render.plain("Not implemented").toFuture
  }

  get("/users/:username") { request =>
    val username = request.routeParams("username")
    render.json(Users.byUsername(username)).toFuture
  }

  put("/users/:username") { request =>
    render.plain("Not implemented").toFuture
  }

  delete("/users/:username") { request =>
    render.plain("Not implemented").toFuture
  }
}
