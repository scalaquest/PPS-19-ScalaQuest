package io.github.scalaquest.core.model

/**
 * A representation a single line of output to render to the user at the end of the pipeline round.
 */
trait Message

/**
 * Interface for a message that involve a [[Model.Item]].
 * @tparam I
 *   the concrete type of [[Model.Item]]
 */
trait ItemMessage[I <: Model#Item] extends Message {

  /**
   * The [[Model.Item]] involved.
   * @return
   *   the item
   */
  def item: I
}

/**
 * Interface for a message that involve a specific [[Model.Room]] and the [[Model.Item]] s that room
 * contains.
 * @tparam I
 *   the concrete type for [[Model.Item]] s.
 * @tparam RM
 *   the concrete type for [[Model.Room]].
 */
trait RoomConfigMessage[I <: Model#Item, RM <: Model#Room] extends Message {
  def room: RM
  def items: Set[I]
}

/**
 * Interface for a message that involve a specific [[Model.Room]].
 * @tparam RM
 *   the concrete type for [[Model.Room]].
 */
trait RoomMessage[RM <: Model#Room] extends Message {
  def room: RM
}

/**
 * Message used when input isn't recognized by the system.
 */
case object NotRecognizedMessage extends Message
