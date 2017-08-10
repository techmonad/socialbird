package com.socialbird.engine

import com.socialbird.engine.utils.{SparkEngine, SparkSessionSingleton}
import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.twitter.TwitterUtils
import twitter4j.Status
import twitter4j._
import org.apache.spark.sql.functions._

/**
  * Created by anand on 4/6/17.
  */
object TwitterProcessor extends App with SparkEngine {

  // Creating a stream from Twitter (see the README to learn how to
  // provide a configuration to make this work - you'll basically
  // need a set of Twitter API keys)
  // var filters = Array ("OKewskdnXXKQezNxK0l5gpIFT","7A1L0Y0UNZcRZTUyDpa050YmuHt6Fl3nB8KiglZ3wEs9WIBgkX","3012524486-2h4rG3LFGLbT9BmTgrqTXHIRqFHVZr95soELaLA","U4exT9cltzGO2GaLVPnJ3sLxLVBfJ3JEC9fnzhgXMbJtA")
  //var filters =Array("@anuragvnl")
  val tweets: DStream[Status] = TwitterUtils.createStream(streamingContext, None)
  //val tweets = TwitterUtils.createStream(streamingContext,None)
  //val q: FilterQuery = new FilterQuery()
  //val t = tweets.filter( Status().getId == 18839785 )


    //val tweets = TwitterUtils.createStream(streamingContext,None)


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


      val filteredDf = dataFrame.filter(
        $"user.id" === 18839785L or
        $"user.id" === 2183816041L or
        $"user.id" === 1346439824L or $"user.id" === 2222673457L
        or  $"user.id" === 130104041L or  $"user.id" === 60937837L or $"user.id" === 371730289L or  $"user.id" === 59983585L
        or  $"user.id" === 50943008L or  $"user.id" === 1910442534L or  $"user.id" === 19760270L or  $"user.id" === 24705126L
        or  $"user.id" === 405427035L or  $"user.id" === 30417501L or  $"user.id" === 141633175L or  $"user.id" === 1346439824L
        or  $"user.id" === 1317076194L or  $"user.id" === 1324334436L or  $"user.id" === 97217966L or  $"user.id" === 1072993274L
        or  $"user.id" === 141208596L or  $"user.id" === 347035710L or  $"user.id" === 3097503906L or  $"user.id" === 12892809359L
        or  $"user.id" === 3066787711L or  $"user.id" === 879284103022956544L or  $"user.id" === 786958314L or  $"user.id" === 143409075L
        or  $"user.id" === 2526794479L or  $"user.id" === 1856414335L or  $"user.id" === 77732185L or  $"user.id" === 373280952L
        or  $"user.id" === 1251083774L or  $"user.id" === 85221650L

      )

      //filteredDf.show()
      val otherDf = dataFrame.filter(
      $"text".contains("@narendramodi") or $"text".contains("@arunjaitley") or $"text".contains("@rajnathsingh") or $"text".contains("@rsprasad") or
      $"text".contains("@smritiirani") or $"text".contains("@AmitShah") or $"text".contains("@Swamy39") or $"text".contains("@digvijaya_28") or
      $"text".contains("@ajaymaken") or $"text".contains("@JhaSanjay") or $"text".contains("@KapilSibal") or $"text".contains("@priyankac19") or
      $"text".contains("@ShashiTharoor") or $"text".contains("@ArvindKejriwal") or $"text".contains("@msisodia") or $"text".contains("@DrKumarVishwas") or
      $"text".contains("@_YogendraYadav") or $"text".contains("@PiyushGoyal") or $"text".contains("@nitin_gadkari") or $"text".contains("@PrakashJavdekar") or
      $"text".contains("@sureshpprabhu") or $"text".contains("@manoharparrikar") or $"text".contains("@PChidambaram_IN") or $"text".contains("@JM_Scindia") or
      $"text".contains("@rssurjewala") or $"text".contains("@meira_kumar") or $"text".contains("@laluprasadrjd") or $"text".contains("@NitishKumar") or
      $"text".contains("@MamataOfficial") or $"text".contains("@Gen_VKSingh") or $"text".contains("@drharshvardhan") or $"text".contains("@drramansingh") or
      $"text".contains("@ChouhanShivraj") or $"text".contains("@ncbn")

      )
      //otherDf.show()

      import org.elasticsearch.spark.sql._
      filteredDf.saveToEs("tweet/users")
      otherDf.saveToEs("other/users")
    }
  }

  // Now that the streaming is defined, start it
  start()

  // Let's await the stream to end - forever
  awaitTermination()

}
