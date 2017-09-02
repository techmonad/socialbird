package com.socialbird.scheduler.utils

import com.socialbird.common.helpers.PlatformConfig

object SchedulerConf extends PlatformConfig("scheduler") {

  /**
    * Twitter configurations
    */
  val consumerKey: String = getString("oauth.consumerKey")
  val consumerSecret: String = getString("oauth.consumerSecret")
  val accessToken: String = getString("oauth.accessToken")
  val accessTokenSecret: String = getString("oauth.accessTokenSecret")

}
