package com.socialbird.scheduler.utils

import com.socialbird.common.helpers.PlatformConfig

object SchedulerConf extends PlatformConfig("scheduler") {

  import com.socialbird.scheduler.constants.SchedulerConstant._

  /**
    * Twitter configurations
    */
  val consumerKey: String = getString(OAUTH_CONSUMER_KEY)
  val consumerSecret: String = getString(OAUTH_CONSUMER_SECRET)
  val accessToken: String = getString(OAUTH_ACCESS_TOKEN)
  val accessTokenSecret: String = getString(OAUTH_ACCESS_TOKEN_SECRET)

}
