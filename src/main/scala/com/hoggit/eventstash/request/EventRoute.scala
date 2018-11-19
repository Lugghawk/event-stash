package com.hoggit.eventstash.request

import akka.actor.{ ActorSystem, Props }
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.server._
import akka.http.scaladsl.server.Directives._
import akka.pattern.ask
import akka.stream.ActorMaterializer
import akka.util.Timeout
import com.typesafe.scalalogging.StrictLogging

import com.hoggit.eventstash.{ Event, RawEvent }

import scala.concurrent.duration._

class EventRoute(actor: Props)(implicit system: ActorSystem) extends StrictLogging with SprayJsonSupport {
  implicit val materializer = ActorMaterializer()
  implicit val to = Timeout(1.second)

  lazy val routes: Route = pathSingleSlash {
    complete("hello")
  } ~ path("event") {
    post {
      entity(as[RawEvent]) { rawEvent =>
        complete(Event(rawEvent))
      }
    }
  }

  def run() {
    Http().bindAndHandle(routes, "0.0.0.0", 8080)
  }
}
