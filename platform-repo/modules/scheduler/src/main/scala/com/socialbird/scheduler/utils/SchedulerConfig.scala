package com.socialbird.scheduler.utils

import com.socialbird.common.helpers.PlatformConfig

object SchedulerConfig extends PlatformConfig("scheduler") {

  import com.socialbird.scheduler.constants.SchedulerConstant._

  val actorSystemName: String = getString(SCHEDULER_ACTOR_SYSTEM_NAME_KEY)

}
