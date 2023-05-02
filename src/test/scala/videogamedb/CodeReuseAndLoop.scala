package videogamedb

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class CodeReuseAndLoop extends Simulation {

  val httpProtocol = http.baseUrl("https://videogamedb.uk/api")
    .acceptHeader("application/json")

  def getAllVideoGames() = {
    exec(http("Get all video games")
      .get("/videogame")
      .check(status.is(200)))
  }

  //looping
  def getSpecificGame() = {
    repeat(3,"counter") {
      exec(http("Get specific game id : #{counter}")
        .get("/videogame/#{counter}")
        .check(status.in(200 to 210)))
    }
  }

  val scn = scenario("Code resuse")
    .exec(getAllVideoGames())
    .pause(2)
    .exec(getSpecificGame())
    .pause(2)
    .exec(getAllVideoGames())
    .repeat(3)  //loop
  {
    getSpecificGame()
  }

  setUp(
    scn.inject(atOnceUsers(1))
  ).protocols(httpProtocol)

}