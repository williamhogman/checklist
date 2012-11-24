package checklist
package controllers

import com.twitter.finatra.Controller
import com.twitter.util.Future

class ListController extends Controller {
  get("/users/:username/lists") { request =>
    render.plain("Not implemented").toFuture
  }

  post("/users/:username/lists") { request =>
    render.plain("Not implemented").toFuture
  }

  get("/users/:username/lists/:listname") { request =>
    render.plain("Not implemented").toFuture
  }

  post("/users/:username/lists/:listname") { request =>
    render.plain("Not implemented").toFuture
  }

  put("/users/:username/lists/:listname") { request =>
    render.plain("Not implemented").toFuture
  }

  delete("/users/:username/lists/:listname") { request =>
    render.plain("Not implemented").toFuture
  }
}
