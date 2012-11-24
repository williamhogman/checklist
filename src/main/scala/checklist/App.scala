package checklist

import checklist.controllers._
import com.twitter.finatra.FinatraServer

object App {
  def main(args: Array[String]) {
    FinatraServer.register(new UserController)
    FinatraServer.register(new PrototypeController)
    FinatraServer.register(new ListController)
    FinatraServer.start()
  }
}
