name := "gatling-test"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
 "io.gatling" % "gatling-app" % "2.2.3",
 "io.gatling.highcharts" % "gatling-charts-highcharts" % "2.2.3" exclude("io.gatling", "gatling-recorder"),
 "com.typesafe.akka" %% "akka-stream" % "2.4.16"
)


