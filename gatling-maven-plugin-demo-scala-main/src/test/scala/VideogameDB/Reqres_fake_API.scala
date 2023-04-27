package VideogameDB
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration.DurationInt


class Reqres_fake_API extends Simulation{

  val httpProtocol = http.baseUrl(url = "https://reqres.in/api")
    .acceptHeader(value = "application/json")

  //scenario defination

  val scn = scenario(name = "Get User Details")
    .exec(http(requestName = "List Users")
    .get("/users?page=2")
    .check(status.is(200)))
//    .check(jsonPath(path = "$.data..first_name").in(expected = "Byron")))
    .pause(500.milliseconds)


    .exec(http(requestName = "Single User")
    .get("/users/2")
//    .check(status.in(200 to 210)))
    .check(jsonPath(path = "$.data.first_name").is(expected = "Janet")))
    .pause(duration = 1)


    .exec(http(requestName = "List Resource")
    .get("/unknown")
    .check(status.not(400),status.not(500)))
    .pause(1,5)


  //Load scenario

  setUp(
    scn.inject(atOnceUsers(users = 1))
      .protocols(httpProtocol)
  )
}
