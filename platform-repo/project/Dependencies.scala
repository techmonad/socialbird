import sbt._

object Dependencies {

  val resolutionRepos = Seq(
    "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/"
  )

  def compile(deps: ModuleID*): Seq[ModuleID] = deps map (_ % "compile")

  def provided(deps: ModuleID*): Seq[ModuleID] = deps map (_ % "provided")

  def test(deps: ModuleID*): Seq[ModuleID] = deps map (_ % "test")

  def runtime(deps: ModuleID*): Seq[ModuleID] = deps map (_ % "runtime")

  def container(deps: ModuleID*): Seq[ModuleID] = deps map (_ % "container")

  // Versions
  object V {
    val Scala = "2.11.11"
    val Akka = "2.5.4"
    val Play = "2.6.1"
    val Spark = "2.1.0"
    val NLP = "3.7.0"
    val Elastic4s = "5.4.7"
    val Twitter4j = "4.0.6"
  }

  object ScalaLib {
    lazy val config: ModuleID = "com.typesafe" % "config" % "1.3.1"
    lazy val logger: ModuleID = "com.typesafe.scala-logging" %% "scala-logging" % "3.7.1"
    lazy val scalatest: ModuleID = "org.scalatest" %% "scalatest" % "3.0.4"
  }

  object AkkaLib {
    lazy val actor: ModuleID = "com.typesafe.akka" %% "akka-actor" % V.Akka
    lazy val testkit: ModuleID = "com.typesafe.akka" %% "akka-testkit" % V.Akka
  }

  object PlayLib {
    val typesafe = "com.typesafe.play"
    lazy val guice: ModuleID = typesafe %% "play-guice" % V.Play
    lazy val jdbc: ModuleID = typesafe %% "play-jdbc" % V.Play
    lazy val cache: ModuleID = typesafe %% "play-cache" % V.Play
    lazy val ws: ModuleID = typesafe %% "play-ws" % V.Play
    lazy val json: ModuleID = typesafe %% "play-json" % V.Play
    lazy val filter: ModuleID = typesafe %% "filters-helpers" % V.Play
    lazy val scalatestplus: ModuleID = "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.0"
  }

  object SparkLib {
    val spark = "org.apache.spark"
    lazy val sparkCore: ModuleID = spark %% "spark-core" % V.Spark
    lazy val sparkSQL: ModuleID = spark %% "spark-sql" % V.Spark
    lazy val sparkStreaming: ModuleID = spark %% "spark-streaming" % V.Spark
    lazy val sparkTwitter: ModuleID = "org.apache.bahir" %% "spark-streaming-twitter" % V.Spark
    lazy val jackson: ModuleID = "com.fasterxml.jackson.core" % "jackson-databind" % "2.6.7"
    lazy val elastic: ModuleID = "org.elasticsearch" % "elasticsearch-spark-20_2.11" % "6.0.0-alpha2"
  }

  object NLPLib {
    val nlp = "edu.stanford.nlp"
    lazy val corenlp: ModuleID = nlp % "stanford-corenlp" % V.NLP
    lazy val models: ModuleID = nlp % "stanford-corenlp" % V.NLP classifier "models"
  }

  object Elastic4sLib {
    lazy val core: ModuleID = "com.sksamuel.elastic4s" %% "elastic4s-core" % V.Elastic4s
    // for the tcp client
    lazy val tcp: ModuleID = "com.sksamuel.elastic4s" %% "elastic4s-tcp" % V.Elastic4s
    // for the http client
    lazy val http: ModuleID = "com.sksamuel.elastic4s" %% "elastic4s-http" % V.Elastic4s
    // if you want to use reactive streams
    lazy val streams: ModuleID = "com.sksamuel.elastic4s" %% "elastic4s-streams" % V.Elastic4s
    // testing
    lazy val testkit: ModuleID = "com.sksamuel.elastic4s" %% "elastic4s-testkit" % V.Elastic4s
    lazy val embedded: ModuleID = "com.sksamuel.elastic4s" %% "elastic4s-embedded" % V.Elastic4s
    // https://mvnrepository.com/artifact/com.google.code.gson/gson
    lazy val gson: ModuleID = "com.google.code.gson" % "gson" % "2.8.0"
  }

  object Twitter4jLib {
    lazy val stream: ModuleID = "org.twitter4j" % "twitter4j-stream" % V.Twitter4j
  }


  val playDependencies: Seq[ModuleID] = {
    import PlayLib._
    compile(guice, jdbc, cache, ws, json, filter) ++ test(scalatestplus)
  }

  val commonDependencies: Seq[ModuleID] = {
    import ScalaLib._
    compile(config, logger) ++ compile(Elastic4sLib.tcp, Twitter4jLib.stream) ++ test(scalatest)
  }

  val apiDependencies: Seq[ModuleID] = {
    import Elastic4sLib._
    playDependencies ++ compile(core, tcp, http, streams) ++ test(testkit, embedded)
  }

  val engineDependencies: Seq[ModuleID] = {
    import SparkLib._
    import NLPLib._
    compile(sparkCore, sparkSQL, sparkStreaming, sparkTwitter, corenlp, models, elastic)
  }

  val schedulerDependencies: Seq[ModuleID] = {
    import AkkaLib._
    commonDependencies ++ compile(actor, Elastic4sLib.gson) ++ test(AkkaLib.testkit, Elastic4sLib.testkit)
  }

}
