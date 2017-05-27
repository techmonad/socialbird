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
    val Play = "2.5.15"
    val Spark = "2.1.1"
  }

  object PlayLib {
    val jdbc = "com.typesafe.play" %% "play-jdbc" % V.Play
    val cache = "com.typesafe.play" %% "play-cache" % V.Play
    val ws = "com.typesafe.play" %% "play-ws" % V.Play
    val json = "com.typesafe.play" %% "play-json" % V.Play
    val filter = "com.typesafe.play" %% "filters-helpers" % V.Play
    val scalatestplus = "org.scalatestplus.play" %% "scalatestplus-play" % "2.0.0"
  }

  object SparkLib {
    val spark = "org.apache.spark"
    lazy val sparkCore = spark %% "spark-core" % V.Spark
    lazy val sparkSQL = spark %% "spark-sql" % V.Spark
    lazy val sparkStreaming = spark %% "spark-streaming" % V.Spark
    lazy val sparkKafka = spark %% "spark-streaming-kafka" % V.Spark
    lazy val sparkTwitter = spark %% "spark-streaming-twitter" % V.Spark
  }

  val playDependencies: Seq[ModuleID] = {
    import PlayLib._
    compile(jdbc, cache, ws, json, filter) ++ test(scalatestplus)
  }

  val apiDependencies: Seq[ModuleID] = {
    playDependencies
  }

}


