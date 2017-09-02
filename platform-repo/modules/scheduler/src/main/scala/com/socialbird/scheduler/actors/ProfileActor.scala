package com.socialbird.scheduler.actors

import akka.actor.{Actor, ActorLogging, Props}
import scala.concurrent.duration._

import scala.concurrent.ExecutionContext.Implicits.global

class ProfileActor extends Actor with ActorLogging {

  import ProfileActor._

  def receive: PartialFunction[Any, Unit] = {
    case Message(id) =>
      log.info("In ProfileActor - received message: {}", id)
      context.system.scheduler.scheduleOnce(5 seconds, self, Message(id))
  }

}

object ProfileActor {
  val props: Props = Props[ProfileActor]
  case class Message(id: Long)
}
