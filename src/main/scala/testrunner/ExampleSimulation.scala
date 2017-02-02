package testrunner


import io.gatling.core.Predef._
import io.gatling.http.Predef._
import protocol.Predef._

class ExampleSimulation extends Simulation {
  val upperProtocol = upper.endpoint("127.0.0.1",8888)
  val scn = scenario("test").exec(upper("user").connect)
  setUp(
    scn.inject(atOnceUsers(250))
  ).protocols(upperProtocol)
}
