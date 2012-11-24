package checklist
package controllers

import com.twitter.finatra.Controller
import com.twitter.util.Future

import checklist.models.UserModel

class PrototypeController extends Controller {
  get("/users/:username/prototypes") { request =>
    render.plain("Not implemented").toFuture
  }

  post("/users/:username/prototypes") { request =>
    render.plain("Not implemented").toFuture
  }

  get("/users/:username/prototypes/:protoId") { request =>
    val username = request.routeParams("username")
    val protoId = request.routeParams("protoId").toInt

    val user = UserModel(username)
    val prototype = user.prototypes(protoId)

    render.json(prototype.dbObject).toFuture
  }

  post("/users/:username/prototypes/:protoId") { request =>
    render.plain("Not implemented").toFuture
  }

  put("/users/:username/prototypes/:protoId") { request =>
    render.plain("Not implemented").toFuture
  }

  delete("/users/:username/prototypes/:protoId") { request =>
    render.plain("Not implemented").toFuture
  }
}
