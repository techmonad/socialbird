package com.socialbird.engine.utils

import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by anand on 4/6/17.
  */
trait SparkEngine {

  val appName: String = "spark-twitter-stream-processor"

  // First, let's configure Spark
  // We have to at least set an application name and master
  // If no master is given as part of the configuration we
  // will set it to be a local deployment running an
  // executor per thread
  val sparkConfiguration: SparkConf = new SparkConf().
    setAppName(appName).setMaster(sys.env.getOrElse("spark.master", "local[*]")).
    set("es.index.auto.create", "true")

  // Let's create the Spark Context using the configuration we just created
  val sparkContext = new SparkContext(sparkConfiguration)

  // Now let's wrap the context in a streaming one, passing along the window size
  val streamingContext = new StreamingContext(sparkContext, Seconds(5))

  // Now that the streaming is defined, start it
  def start(): Unit = streamingContext.start()

  // Let's await the stream to end - forever
  def awaitTermination(): Unit = streamingContext.awaitTermination()

}
