package com.socialbird.scheduler.actors

import akka.actor.{Actor, ActorLogging, Props}
import com.socialbird.scheduler.processes.{IngestProcess, TwitterProcess}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

class ProfileActor extends Actor with ActorLogging {

  import ProfileActor._
  import com.socialbird.scheduler.constants.SchedulerConstant._

  def receive: PartialFunction[Any, Unit] = {
    case Message(id) =>
      log.info("In ProfileActor - received message: {}", id)
      val data: String = TwitterProcess.getUser(id)
      log.info("In ProfileActor - received user data: {}", data)
      IngestProcess.ingest(ELASTIC_INDEX_NAME, ELASTIC_POLITICIANS_TYPE_NAME, data).map(status =>
        log.info("In ProfileActor - ingest status: {}", status)
      )
      context.system.scheduler.scheduleOnce(24 hours, self, Message(id))
  }

}

object ProfileActor {
  val props: Props = Props[ProfileActor]

  case class Message(id: Long)

}
