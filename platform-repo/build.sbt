import BuildSettings._
import Dependencies._

// -------------------------------------------------------------------------------------------------------------------
// Root Project
// -------------------------------------------------------------------------------------------------------------------
lazy val root = project.in(file("."))
  .aggregate(common, api, engine)
  .dependsOn(common, api, engine)
  .settings(basicSettings: _*)
  .settings(dependencyOverrides ++= Set(SparkLib.jackson))

// -------------------------------------------------------------------------------------------------------------------
// Common Module
// -------------------------------------------------------------------------------------------------------------------
lazy val common = project.in(file("modules/common"))
  .settings(basicSettings: _*)
  .settings(libraryDependencies ++= commonDependencies)

// -------------------------------------------------------------------------------------------------------------------
// REST API APP
// -------------------------------------------------------------------------------------------------------------------
lazy val api = project.in(file("modules/api"))
  .dependsOn(common)
  .enablePlugins(PlayScala)
  .settings(basicSettings: _*)
  .settings(playScalaSettings: _*)
  .settings(libraryDependencies ++= apiDependencies)

// -------------------------------------------------------------------------------------------------------------------
// Engine Module
// -------------------------------------------------------------------------------------------------------------------
lazy val engine = project.in(file("modules/engine"))
  .dependsOn(common)
  .settings(basicSettings: _*)
  .settings(libraryDependencies ++= engineDependencies)
  .settings(dependencyOverrides ++= Set(SparkLib.jackson))


