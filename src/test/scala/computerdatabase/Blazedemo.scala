package computerdatabase

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class Blazedemo extends Simulation {

  private val httpProtocol = http
    .baseUrl("https://blazedemo.com")
    .inferHtmlResources(AllowList(), DenyList(""".*\.js""", """.*\.css""", """.*\.gif""", """.*\.jpeg""", """.*\.jpg""", """.*\.ico""", """.*\.woff""", """.*\.woff2""", """.*\.(t|o)tf""", """.*\.png""", """.*detectportal\.firefox\.com.*"""))
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8")
    .acceptEncodingHeader("gzip, deflate")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .upgradeInsecureRequestsHeader("1")
    .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:109.0) Gecko/20100101 Firefox/112.0")
  
  private val headers_0 = Map("Accept-Encoding" -> "gzip, deflate")
  
  private val headers_1 = Map(
  		"Origin" -> "https://blazedemo.com",
  		"Sec-Fetch-Dest" -> "document",
  		"Sec-Fetch-Mode" -> "navigate",
  		"Sec-Fetch-Site" -> "same-origin",
  		"Sec-Fetch-User" -> "?1"
  )
  
  private val uri1 = "blazedemo.com"

  private val scn = scenario("RecordedSimulation")
    .exec(
      http("HomePage")
        .get("http://" + uri1 + "/")
        .headers(headers_0)
    )
    .pause(31)
    // searchflight
    .exec(
      http("Search Flight")
        .post("/reserve.php")
        .headers(headers_1)
        .formParam("fromPort", "Paris")
        .formParam("toPort", "Buenos Aires")
    )
    .pause(12)
    // Select Flight
    .exec(
      http("Select Flight")
        .post("/purchase.php")
        .headers(headers_1)
        .formParam("flight", "43")
        .formParam("price", "472.56")
        .formParam("airline", "Virgin America")
        .formParam("fromPort", "Paris")
        .formParam("toPort", "Buenos Aires")
    )
    .pause(58)
    // purchaseFlight
    .exec(
      http("Purchase flight")
        .post("/confirmation.php")
        .headers(headers_1)
        .formParam("_token", "")
        .formParam("inputName", "Hariprasad")
        .formParam("address", "abc")
        .formParam("city", "bangalore")
        .formParam("state", "Karnataka")
        .formParam("zipCode", "560083")
        .formParam("cardType", "visa")
        .formParam("creditCardNumber", "123456789")
        .formParam("creditCardMonth", "11")
        .formParam("creditCardYear", "2024")
        .formParam("nameOnCard", "Hariprasad")
    )

	setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}
