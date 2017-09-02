package com.socialbird.scheduler.actors

import akka.actor.{Actor, ActorLogging, Props}
import scala.concurrent.duration._

import scala.concurrent.ExecutionContext.Implicits.global

class TimelineActor extends Actor with ActorLogging {

  import TimelineActor._

  def receive: PartialFunction[Any, Unit] = {
    case Message(id) =>
      log.info("In TimelineActor - received message: {}", id)
      context.system.scheduler.scheduleOnce(5 seconds, self, Message(id))
  }

}

object TimelineActor {

  val props: Props = Props[TimelineActor]

  case class Message(id: Long)

}
