package com.socialbird.scheduler

import java.util
import java.util.List

import com.sksamuel.elastic4s.{ElasticsearchClientUri, TcpClient}
import com.socialbird.common.utils.TwitterUtility
import com.socialbird.scheduler.utils.SchedulerConf
import org.elasticsearch.action.support.WriteRequest.RefreshPolicy
import twitter4j.TwitterFactory
import twitter4j.conf._

import scala.collection.JavaConversions._


/**
  * Created by Sanjul on 31/08/17.
  */
object TweetScheduler extends App {

  val cb = new ConfigurationBuilder()

  cb.setDebugEnabled(true)
    .setOAuthConsumerKey(SchedulerConf.consumerKey)
    .setOAuthConsumerSecret(SchedulerConf.consumerSecret)
    .setOAuthAccessToken(SchedulerConf.accessToken)
    .setOAuthAccessTokenSecret(SchedulerConf.accessTokenSecret)

  val factory = new TwitterFactory(cb.build())
  val twitter = factory.getInstance()

  var instance: TcpClient = TcpClient.transport(ElasticsearchClientUri("localhost", 9300))

  import com.sksamuel.elastic4s.ElasticDsl._

  for (twitterId <- SchedulerConf.politicianIds) {
    val statusList: util.List[twitter4j.Status] = twitter.getUserTimeline(twitterId)
    for (inVal <- statusList) {
      val tweet: com.socialbird.common.domains.Tweet = TwitterUtility.statusToTweet(inVal)
      val gson: com.google.gson.Gson = new com.google.gson.Gson()
      val tweetJson: String = gson.toJson(tweet)
      instance.execute {
        indexInto("socialbird" / "politicianTweets") doc tweetJson refresh (RefreshPolicy.IMMEDIATE)
      }
    }
  }
  println("===========All tweets saved successfully===========")

  instance.execute {
    deleteIn("socialbird/politicians").by(matchAllQuery())
  }
  println("socialbird/politicians index deleted/flushed")

  for (twitterId <- SchedulerConf.politicianIds) {
    val user: twitter4j.User = twitter.showUser(twitterId)
    val usr: com.socialbird.common.domains.User = TwitterUtility.tweetUserToUser(user)
    val gson: com.google.gson.Gson = new com.google.gson.Gson()
    val userJson: String = gson.toJson(usr)
    instance.execute {
      indexInto("socialbird" / "politicians") doc userJson refresh (RefreshPolicy.IMMEDIATE)
    }.await
    println("Saving details of |" + user.getScreenName() + "|")
  }
  println("==========Politicians details are saved successfully=============")

}