package connect4.DB

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class LoadTest extends Simulation {

  val httpProtocol = http
    // Here is the root for all relative URLs
    .baseUrl("http://localhost:8080")
    // Here are the common headers
    .acceptHeader(
      "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8"
    )
    .doNotTrackHeader("1")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .acceptEncodingHeader("gzip, deflate")
    .userAgentHeader(
      "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0"
    )

  // A scenario is a chain of requests and pauses
  val load = scenario("Testing Database - Load")
    .exec(
      http("update databse - load")
        .post("/model/grid/saveDB")
    )
    .pause(2)
    .exec(
      http("read database - load")
        .post("/model/grid/load")
    )

  setUp(
    load.inject(atOnceUsers(2)),
  ).protocols(httpProtocol)

}
