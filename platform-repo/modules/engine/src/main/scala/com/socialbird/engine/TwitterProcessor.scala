package com.socialbird.engine

import com.socialbird.engine.utils.{SparkEngine, SparkSessionSingleton}
import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.twitter.TwitterUtils
import twitter4j.Status
import org.apache.spark.sql.functions._

/**
  * Created by anand on 4/6/17.
  */
object TwitterProcessor extends App with SparkEngine {

  // Creating a stream from Twitter (see the README to learn how to
  // provide a configuration to make this work - you'll basically
  // need a set of Twitter API keys)
 // var filters = Array ("OKewskdnXXKQezNxK0l5gpIFT","7A1L0Y0UNZcRZTUyDpa050YmuHt6Fl3nB8KiglZ3wEs9WIBgkX","3012524486-2h4rG3LFGLbT9BmTgrqTXHIRqFHVZr95soELaLA","U4exT9cltzGO2GaLVPnJ3sLxLVBfJ3JEC9fnzhgXMbJtA")
  var filters =Array("@narendramodi","@PMOIndia")
  val tweets: DStream[Status] = TwitterUtils.createStream(streamingContext,None,filters)

  tweets.foreachRDD { rdd =>

    if (!rdd.isEmpty()) {
      // Get the singleton instance of SparkSession
      val sparkSession = SparkSessionSingleton.getInstance(rdd.sparkContext.getConf)

      import com.socialbird.engine.utils.TwitterUtility._
      import sparkSession.implicits._
      import com.socialbird.engine.analysis.SentimentAnalysis._

      // Convert RDD[String] to DataFrame
      val dataFrame = rdd.map(statusToTweet).toDF().withColumn("sentiment", sentiment('text))
        .withColumn("publish_timestamp", current_timestamp())
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
