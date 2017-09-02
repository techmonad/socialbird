package com.socialbird.scheduler

import akka.actor.ActorSystem
import com.socialbird.scheduler.utils.SchedulerConf

object ApplicationMain extends App {
  println(SchedulerConf.getString("oauth.consumerKey"))
  val system = ActorSystem("MyActorSystem")
  val pingActor = system.actorOf(PingActor.props, "pingActor")
  pingActor ! PingActor.Initialize
  // This example app will ping pong 3 times and thereafter terminate the ActorSystem - 
  // see counter logic in PingActor
  system.whenTerminated
}