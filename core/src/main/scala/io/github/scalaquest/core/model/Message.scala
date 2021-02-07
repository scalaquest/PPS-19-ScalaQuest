package io.github.scalaquest.core.model

/**
 * A representation of an event occurred into the [[Model.State]], following a [[Model.Reaction]].
 */
abstract class Message

/**
 * A message that keeps track of an [[Model.Item]] involved into the occurred event.
 * @tparam I
 *   The concrete type of [[Model.Item]]
 */
trait ItemMessage[I <: Model#Item] extends Message {
  def item: I
}

/**
 * A message that keeps track of a [[Model.Room]].
 * @tparam RM
 *   the concrete type for [[Model.Room]].
 */
trait RoomMessage[RM <: Model#Room] extends Message {
  def room: RM
}
