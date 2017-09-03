package com.socialbird.common.configs

import com.socialbird.common.constants.CommonConstant._
import com.socialbird.common.helpers.PlatformConfig

object CommonConfig extends PlatformConfig(COMMON_CONF_NAME) {

  /**
    * Twitter configurations
    */
  val consumerKey: String = getString(OAUTH_CONSUMER_KEY)
  val consumerSecret: String = getString(OAUTH_CONSUMER_SECRET)
  val accessToken: String = getString(OAUTH_ACCESS_TOKEN)
  val accessTokenSecret: String = getString(OAUTH_ACCESS_TOKEN_SECRET)

  val politicianIds: List[Long] = getLongList(POLITICIANS_TWITTER_IDS)
  val politicianHandles: List[String] = getStringList(POLITICIANS_TWITTER_HANDLES)

}
