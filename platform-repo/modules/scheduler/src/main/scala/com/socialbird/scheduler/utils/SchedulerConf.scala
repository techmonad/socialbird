package com.socialbird.scheduler.utils

import com.socialbird.common.helpers.PlatformConfig

object SchedulerConf extends PlatformConfig("scheduler") {

  import com.socialbird.scheduler.constants.SchedulerConstant._

  val actorSystemName: String = getString(SCHEDULER_ACTOR_SYSTEM_NAME_KEY)

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
