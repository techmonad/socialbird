package com.socialbird.engine

import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.twitter.TwitterUtils
import twitter4j.Status

/**
  * Created by anand on 4/6/17.
  */
object TwitterProcessor extends App with SparkEngine {

  // Creating a stream from Twitter (see the README to learn how to
  // provide a configuration to make this work - you'll basically
  // need a set of Twitter API keys)
  val tweets: DStream[Status] = TwitterUtils.createStream(streamingContext, None)

  tweets.foreachRDD { rdd =>

    if (!rdd.isEmpty()) {
      // Get the singleton instance of SparkSession
      val sparkSession = SparkSessionSingleton.getInstance(rdd.sparkContext.getConf)

      import TwitterUtility._
      import sparkSession.implicits._
      import SentimentAnalysis._

      // Convert RDD[String] to DataFrame
      val dataFrame = rdd.map(statusToTweet).toDF().withColumn("sentiment", sentiment('text))
      dataFrame.show()

      import org.elasticsearch.spark.sql._
      dataFrame.saveToEs("tweet/analysis")
    }
  }

  // Now that the streaming is defined, start it
  start()

  // Let's await the stream to end - forever
  awaitTermination()

}
