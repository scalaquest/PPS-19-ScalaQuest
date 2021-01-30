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

/**
 * A message that keeps track of a [[Model.Room]] and the [[Model.Item]] s contained into it.
 * @tparam I
 *   the concrete type for [[Model.Item]] s.
 * @tparam RM
 *   the concrete type for [[Model.Room]].
 */
trait RoomCompositionMessage[I <: Model#Item, RM <: Model#Room] extends RoomMessage[RM] {
  def items: Set[I]
}
