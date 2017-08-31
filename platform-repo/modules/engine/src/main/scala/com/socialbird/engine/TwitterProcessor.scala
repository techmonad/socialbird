package com.socialbird.engine

import com.socialbird.engine.utils.{SparkEngine, SparkSessionSingleton}
import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.twitter.TwitterUtils
import twitter4j.Status
import twitter4j._
import com.socialbird.engine.analysis.SentimentAnalysis._
/**
  * Created by anand on 4/6/17.
  */
object TwitterProcessor extends App with SparkEngine {


  val twitterHandlers: List[String] = List("@narendramodi", "@arunjaitley", "@rajnathsingh","@rsprasad","@smritiirani","@AmitShah","@Swamy39","@digvijaya_28","@ajaymaken","@JhaSanjay","@KapilSibal","@priyankac19",
    "@ShashiTharoor","@ArvindKejriwal","@msisodia","@DrKumarVishwas","@_YogendraYadav","@PiyushGoyal","@nitin_gadkari","@PrakashJavdekar","@sureshpprabhu",
    "@manoharparrikar","@PChidambaram_IN","@JM_Scindia","@rssurjewala","@meira_kumar","@laluprasadrjd","@NitishKumar","@MamataOfficial","@Gen_VKSingh","@drharshvardhan","@drramansingh",
    "@ChouhanShivraj","@ncbn")

  // Creating a stream from Twitter (see the README to learn how to
  // provide a configuration to make this work - you'll basically
  // need a set of Twitter API keys)
  val tweets: DStream[Status] = TwitterUtils.createStream(streamingContext, None,twitterHandlers)


  tweets.foreachRDD { rdd =>

    if (!rdd.isEmpty()) {
      // Get the singleton instance of SparkSession
      val sparkSession = SparkSessionSingleton.getInstance(rdd.sparkContext.getConf)

      import com.socialbird.engine.utils.TwitterUtility._
      import sparkSession.implicits._
      import com.socialbird.engine.analysis.SentimentAnalysis._
      import org.apache.spark.sql.functions._

      // Convert RDD[String] to DataFrame
      val dataFrame = rdd.map(statusToTweet).toDF().withColumn("sentiment", sentiment('text))
        .withColumn("publish_timestamp", current_timestamp())

      dataFrame.show()
      import org.elasticsearch.spark.sql._
      dataFrame.saveToEs("socialbird/liveTweets")
    }
  }

  // Now that the streaming is defined, start it
  start()

  // Let's await the stream to end - forever
  awaitTermination()

}
