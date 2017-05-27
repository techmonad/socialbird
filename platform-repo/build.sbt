import BuildSettings._
import Dependencies._

// -------------------------------------------------------------------------------------------------------------------
// Root Project
// -------------------------------------------------------------------------------------------------------------------
lazy val root = project.in(file("."))
  .aggregate(api).settings(basicSettings: _*)

// -------------------------------------------------------------------------------------------------------------------
// REST API
// -------------------------------------------------------------------------------------------------------------------
lazy val api = project.in(file("modules/api"))
  .enablePlugins(PlayScala)
  .settings(basicSettings: _*)
  .settings(playScalaSettings: _*)
  //.dependsOn(common)
  .settings(libraryDependencies ++= apiDependencies)


