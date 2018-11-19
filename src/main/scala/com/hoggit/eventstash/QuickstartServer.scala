package com.hoggit.eventstash

import akka.actor.ActorSystem
import com.hoggit.eventstash.actor.EventStasher
import com.hoggit.eventstash.request.EventRoute
import com.typesafe.scalalogging.StrictLogging

object QuickstartServer extends App with StrictLogging {
  val actor = EventStasher.props()
  implicit val system = ActorSystem("eventstash")
  new Thread(new EventRoute(actor)).start()
  Thread.sleep(5000)
}
