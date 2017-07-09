import play.routes.compiler.InjectedRoutesGenerator
import play.sbt.routes.RoutesCompiler.autoImport.routesGenerator
import sbt.Keys._
import sbt._

object BuildSettings {

  val VERSION = "1.0-SNAPSHOT"

  lazy val basicSettings = Seq(
    version := VERSION,
    homepage := Some(new URL("http://socialbird.com")),
    organization := "SocialBird",
    organizationHomepage := Some(new URL("http://socialbird.com")),
    description := "SocialBird Platform",
    startYear := Some(2017),
    scalaVersion := Dependencies.V.Scala,
    ivyScala := ivyScala.value.map(_.copy(overrideScalaVersion = true)),
    resolvers ++= Dependencies.resolutionRepos,
    parallelExecution in Test := false,
    scalacOptions := Seq("-target:jvm-1.8", "-encoding", "UTF-8", "-deprecation", "-unchecked"),
    shellPrompt := { s => s"[SB PROJECT] ${Project.extract(s).currentProject.id} > " },
    mainClass in (Compile, run) := Some("com.socialbird.engine.TwitterProcessor")
  )

  lazy val playScalaSettings = Seq(
    resolvers ++= Dependencies.resolutionRepos,
    routesGenerator := InjectedRoutesGenerator
  )

  lazy val noPublishing = Seq(
    publish := (),
    publishLocal := ()
  )

}
