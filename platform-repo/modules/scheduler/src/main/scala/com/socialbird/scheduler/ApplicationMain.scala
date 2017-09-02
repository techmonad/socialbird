package com.socialbird.scheduler

import akka.actor.ActorSystem
import com.socialbird.scheduler.actors.{ProfileActor, TimelineActor}
import com.socialbird.scheduler.utils.SchedulerConf

object ApplicationMain extends App {

  val system = ActorSystem(SchedulerConf.actorSystemName)

  val profileActor = system.actorOf(ProfileActor.props, "ProfileActor")
  val timelineActor = system.actorOf(TimelineActor.props, "TimelineActor")

  SchedulerConf.politicianIds foreach { id =>
    profileActor ! ProfileActor.Message(id)
    timelineActor ! TimelineActor.Message(id)
  }

  // This example app will ping pong 3 times and thereafter terminate the ActorSystem - 
  // see counter logic in PingActor
  system.whenTerminated
}