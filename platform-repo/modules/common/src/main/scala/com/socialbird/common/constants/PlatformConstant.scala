package com.socialbird.common.constants

trait PlatformConstant {

  /**
    * Elastic search constants
    */
  val ELASTIC_INDEX_NAME = "socialbird"
  val ELASTIC_POLITICIANS_TYPE_NAME = "politicians"
  val ELASTIC_POLITICIANS_TWEETS_TYPE_NAME = "politicianTweets"

  /**
    * Twitter Constants
    */
  val OAUTH_CONSUMER_KEY: String = "oauth.consumerKey"
  val OAUTH_CONSUMER_SECRET: String = "oauth.consumerSecret"
  val OAUTH_ACCESS_TOKEN: String = "oauth.accessToken"
  val OAUTH_ACCESS_TOKEN_SECRET: String = "oauth.accessTokenSecret"

  val POLITICIANS_TWITTER_HANDLES: String = "twitter.handles.politicians"
  val POLITICIANS_TWITTER_IDS: String = "twitter.ids.politicians"

}
