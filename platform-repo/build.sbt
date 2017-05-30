import BuildSettings._
import Dependencies._

// -------------------------------------------------------------------------------------------------------------------
// Root Project
// -------------------------------------------------------------------------------------------------------------------
lazy val root = project.in(file("."))
  .aggregate(common, api).settings(basicSettings: _*)

// -------------------------------------------------------------------------------------------------------------------
// Common Module
// -------------------------------------------------------------------------------------------------------------------
lazy val common = project.in(file("modules/common"))
  .settings(basicSettings: _*)
  .settings(libraryDependencies ++= playDependencies)

// -------------------------------------------------------------------------------------------------------------------
// REST API APP
// -------------------------------------------------------------------------------------------------------------------
lazy val api = project.in(file("modules/api"))
  .enablePlugins(PlayScala)
  .settings(basicSettings: _*)
  .settings(playScalaSettings: _*)
  .dependsOn(common)
  .settings(libraryDependencies ++= apiDependencies)

// -------------------------------------------------------------------------------------------------------------------
// Engine Module
// -------------------------------------------------------------------------------------------------------------------
lazy val engine = project.in(file("modules/engine"))
  .settings(basicSettings: _*)
  .settings(libraryDependencies ++= engineDependencies)
  .settings(dependencyOverrides ++= Set(SparkLib.jackson))
  .dependsOn(common)


