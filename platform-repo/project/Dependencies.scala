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
    val Play = "2.5.15"
    val Spark = "2.1.1"
  }

  object PlayLib {
    val typesafe = "com.typesafe.play"
    lazy val jdbc: ModuleID = typesafe %% "play-jdbc" % V.Play
    lazy val cache: ModuleID = typesafe %% "play-cache" % V.Play
    lazy val ws: ModuleID = typesafe %% "play-ws" % V.Play
    lazy val json: ModuleID = typesafe %% "play-json" % V.Play
    lazy val filter: ModuleID = typesafe %% "filters-helpers" % V.Play
    lazy val scalatestplus: ModuleID = "org.scalatestplus.play" %% "scalatestplus-play" % "2.0.0"
  }

  object SparkLib {
    val spark = "org.apache.spark"
    lazy val sparkCore: ModuleID = spark %% "spark-core" % V.Spark
    lazy val sparkSQL: ModuleID = spark %% "spark-sql" % V.Spark
    lazy val sparkStreaming: ModuleID = spark %% "spark-streaming" % V.Spark
    lazy val sparkKafka: ModuleID = spark %% "spark-streaming-kafka" % V.Spark
    lazy val sparkTwitter: ModuleID = spark %% "spark-streaming-twitter" % V.Spark
  }

  val playDependencies: Seq[ModuleID] = {
    import PlayLib._
    compile(jdbc, cache, ws, json, filter) ++ test(scalatestplus)
  }

  val apiDependencies: Seq[ModuleID] = {
    playDependencies
  }

}


