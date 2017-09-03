package com.socialbird.common.utils

import com.socialbird.common.configs.CommonConfig
import twitter4j.conf.ConfigurationBuilder
import twitter4j.{Twitter, TwitterFactory}

object TwitterInstance {

  @transient private var instance: Twitter = _

  def getInstance(): Twitter = {
    if (instance == null) {
      val cb = new ConfigurationBuilder()
      cb.setDebugEnabled(true)
        .setOAuthConsumerKey(CommonConfig.consumerKey)
        .setOAuthConsumerSecret(CommonConfig.consumerSecret)
        .setOAuthAccessToken(CommonConfig.accessToken)
        .setOAuthAccessTokenSecret(CommonConfig.accessTokenSecret)

      val factory: TwitterFactory = new TwitterFactory(cb.build())
      instance = factory.getInstance()
    }
    instance
  }

}
