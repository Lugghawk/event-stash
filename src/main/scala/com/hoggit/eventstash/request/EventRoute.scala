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

import scala.concurrent.duration._
import scala.concurrent.Future

class EventRoute(actor: Props)(implicit as: ActorSystem) extends Runnable with StrictLogging with SprayJsonSupport {
  implicit val materializer = ActorMaterializer()
  implicit val to = Timeout(1.second)

  lazy val routes: Route = path("/") {
    complete("hello")
  }

  def run() {
    Http().bindAndHandle(routes, "0.0.0.0", 8080)
  }
}
