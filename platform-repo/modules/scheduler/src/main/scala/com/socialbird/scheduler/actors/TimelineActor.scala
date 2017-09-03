package com.socialbird.scheduler.actors

import akka.actor.{Actor, ActorLogging, Props}
import com.socialbird.scheduler.constants.SchedulerConstant._
import com.socialbird.scheduler.processes.{IngestProcess, TwitterProcess}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

class TimelineActor extends Actor with ActorLogging {

  import TimelineActor._

  def receive: PartialFunction[Any, Unit] = {
    case Message(id) =>
      log.info("In TimelineActor - received message: {}", id)
      val timeline: List[String] = TwitterProcess.getTimeline(id)
      log.info("In TimelineActor - received timeline data: {}", timeline)
      timeline.foreach { data =>
        IngestProcess.ingest(ELASTIC_INDEX_NAME, ELASTIC_POLITICIANS_TWEETS_TYPE_NAME, data).map(status =>
          log.info("In TimelineActor - ingest status: {}", status)
        )
      }
      context.system.scheduler.scheduleOnce(24 hours, self, Message(id))
  }

}

object TimelineActor {

  val props: Props = Props[TimelineActor]

  case class Message(id: Long)

}
