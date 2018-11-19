lazy val hwDockerLogin= taskKey[Boolean]("Checks login status of the build.hoggitworld.com repo. Asks for creds if you're not logged in")
lazy val commonSettings = Defaults.itSettings ++ Seq(
  organization := "com.hoggit",
  version := "0.1-SNAPSHOT",
  scalaVersion := "2.12.7",
  scalacOptions := Seq(
    "-feature",
    "-deprecation",
    "-unchecked",
    "-Ywarn-unused-import",
    "-Ywarn-dead-code",
    "-Yno-adapted-args",
    "-Ywarn-dead-code"
  ),
  libraryDependencies ++= Seq(
      "com.typesafe.scala-logging" %% "scala-logging" % "3.7.2",
      "io.spray" %%  "spray-json" % "1.3.3",
      "ch.qos.logback" % "logback-classic" % "1.2.3",
      "joda-time" % "joda-time" % "2.2",
      "com.typesafe.akka" %% "akka-http" % "10.0.11",
      "com.typesafe.akka" %% "akka-http-spray-json" % "10.0.11",
      "com.typesafe.akka" %% "akka-actor" % "2.4.20",
      "org.scalatest" %% "scalatest" %  "3.0.1" % "test",
      "com.typesafe.akka" %% "akka-testkit" % "2.5.12" % Test
  ),
  dockerRepository := Some("build.hoggitworld.com"),
  dockerBaseImage := "openjdk:jre-alpine",
  publish in Docker := ((publish in Docker).dependsOn(hwDockerLogin)).value,
  hwDockerLogin := {
    val result = "bin/hoggitworld_login.sh".!
    if (result != 0)
      throw new RuntimeException("Not logged into hoggitworld.com docker repo. Please login using `docker login build.hoggitworld.com`")
    true
  }
)

lazy val eventStash= (project in file("."))
  .enablePlugins(JavaAppPackaging)
  .enablePlugins(AshScriptPlugin)
  .settings(commonSettings)
  .settings(
    name := "event-stash",
    mainClass in Compile := Some("com.hoggit.eventstash.QuickstartServer")
  )
