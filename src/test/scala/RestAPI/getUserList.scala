package RestAPI

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration.DurationInt
class getUserList extends Simulation{


  // Http configuration

 val httpProtocol =http.baseUrl("https://reqres.in/api")
  .acceptHeader(value = "application/json")
  .contentTypeHeader("application/json")


  // scenario definition

  val scn = scenario(name = "FirstTest")
    .exec(http("GetUserList")
    .get("/users?page=2")
      .check(status.is(200))
      .check(status.not(404),status.not(500))
      .check(jsonPath("$.data[1].first_name").is("Lindsay")))
      .pause(3000.milliseconds)

  // load scenario

  setUp(
        scn.inject(atOnceUsers(1))
          .protocols(httpProtocol)

      )

}
