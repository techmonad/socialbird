package com.socialbird.scheduler.processes

import com.google.gson.Gson
import com.socialbird.common.utils.{TwitterInstance, TwitterUtility}
import twitter4j.Twitter

import scala.collection.JavaConversions._

object TwitterProcess {

  private val twitter: Twitter = TwitterInstance.getInstance()
  private val gson: Gson = new Gson();

  def getTimeline(twitterId: Long): List[String] = {
    twitter.getUserTimeline(twitterId).map { timeline =>
      gson.toJson(TwitterUtility.statusToTweet(timeline))
    }.toList
  }

  def getUser(twitterId: Long): String = {
    gson.toJson(TwitterUtility.tweetUserToUser(twitter.showUser(twitterId)))
  }

}
