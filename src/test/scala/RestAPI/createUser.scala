package RestAPI
import io.gatling.core.Predef._
import  io.gatling.http.Predef ._
class createUser extends Simulation {


  val httpProtocol = http.baseUrl("https://reqres.in")
    .acceptHeader("application/json")
    .contentTypeHeader("application/json")

  val scn = scenario("Create user")
    .exec(http("crerate a new user")
    .post("/user")
    .body(
      StringBody(""" {
    "name": "morpheus",
    "job": "leader"
  }"""    )).asJson
    )
    .pause(1)

  setUp(
    scn.inject(atOnceUsers(1))
    .protocols(httpProtocol))
}
