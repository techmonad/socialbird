import BuildSettings._
import Dependencies._

scalaVersion := "2.11.11"

// -------------------------------------------------------------------------------------------------------------------
// Root Project
// -------------------------------------------------------------------------------------------------------------------
lazy val root = Project("platform-repo", file("."))
  .aggregate(api).settings(basicSettings: _*)

// -------------------------------------------------------------------------------------------------------------------
// REST API
// -------------------------------------------------------------------------------------------------------------------
lazy val api = Project("api", file("modules/api"))
  .enablePlugins(PlayScala)
  .settings(basicSettings: _*)
  .settings(playScalaSettings: _*)
  //.dependsOn(common)
  .settings(libraryDependencies ++= apiDependencies)


