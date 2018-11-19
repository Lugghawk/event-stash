package com.hoggit.eventstash

import org.joda.time.DateTime
import spray.json._

object Event {
  import DefaultJsonProtocol._

  implicit object dateTimeJsonFormat extends RootJsonFormat[DateTime] {
    def write(dt: DateTime): JsValue = JsString(dt.toString())
    def read(value: JsValue): DateTime = value match {
      case JsString(s) =>
        try {
          DateTime.parse(s)
        } catch {
          case _: Throwable =>
            deserializationError("DateTime expected")
        }
      case _ =>
        deserializationError("DateTime expected")
    }
  }
  implicit val eventFormat: RootJsonFormat[Event] = jsonFormat4(Event.apply)
}

case class Event(target: String, initiator: String, eventType: String, timestamp: DateTime)
