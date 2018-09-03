name := "akka-http-simple-client"

version := "0.1"

scalaVersion := "2.12.6"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http" % "10.1.4",
  "com.typesafe.akka" %% "akka-stream" % "2.5.16",
  "com.typesafe.akka" %% "akka-http-spray-json" % "10.1.4",
  "io.spray" %%  "spray-json" % "1.3.4",
  "com.typesafe.akka" %% "akka-http-testkit" % "10.1.4" % Test,
  "com.typesafe.akka" %% "akka-stream-testkit" % "2.5.16" % Test,
  "com.github.tomakehurst" % "wiremock" % "2.14.0" % Test,
  "org.scalatest" %% "scalatest" % "3.0.1" % Test
)