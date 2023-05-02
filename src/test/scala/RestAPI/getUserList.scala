package RestAPI

import io.gatling.core.Predef._
import io.gatling.http.Predef._
class getUserList extends Simulation{


  // Http configuration

 val httpProtocol =http.baseUrl("https://reqres.in/api")
  .acceptHeader(value = "application/json")
  .contentTypeHeader("application/json")


  // scenario definition

  val scn = scenario(name = "FirstTest")
    .exec(http("GetUserList")
    .get("/users?page=2")
      .check(status.is(expected =200))

  // load scenario

  setUp(
        scn.inject(atOnceUsers(1))
          .protocols(httpProtocol)

      )

}
