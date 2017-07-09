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
    val Play = "2.6.1"
    val Spark = "2.1.0"
    val NLP = "3.7.0"
  }

  object ScalaLib {
    lazy val config = "com.typesafe" % "config" % "1.3.1"
    lazy val logger = "com.typesafe.scala-logging" %% "scala-logging" % "3.7.1"
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

  object ElasticLib {
    lazy val elastic4s = "com.sksamuel.elastic4s" %% "elastic4s-tcp" % "6.0.0-M1"
  }

  val playDependencies: Seq[ModuleID] = {
    import PlayLib._
    compile(guice, jdbc, cache, ws, json, filter) ++ test(scalatestplus)
  }

  val commonDependencies: Seq[ModuleID] = {
    import ScalaLib._
    compile(config, logger)
  }

  val apiDependencies: Seq[ModuleID] = {
    playDependencies // ++ compile(ElasticLib.elastic4s)
  }

  val engineDependencies: Seq[ModuleID] = {
    import SparkLib._
    import NLPLib._
    compile(sparkCore, sparkSQL, sparkStreaming, sparkTwitter, corenlp, models, elastic)
  }

}


