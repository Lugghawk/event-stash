package com.hoggit.eventstash.actor

import akka.actor.{ Actor, Props }
import com.hoggit.eventstash.Event
import com.typesafe.scalalogging.StrictLogging

object EventStasher {
  def props() = Props(new EventStasher())
}

case class StashEvent(event: Event)

class EventStasher() extends Actor with StrictLogging {

  def receive: Receive = {
    case _ => Unit
  }
}
