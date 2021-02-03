package io.github.scalaquest.core.model

/**
 * A representation a single line of output to render to the user at the end of the pipeline round.
 */
trait Message

case class InspectedRoom(room: Model#Room) extends Message

trait ItemMessage[I <: Model#Item] extends Message {
  def item: I
}

trait RoomMessage[RM <: Model#Room] extends Message {
  def room: RM
}

case object NotRecognizedMessage extends Message
