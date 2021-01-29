package io.github.scalaquest.core.model.impl

import io.github.scalaquest.core.model._

/**
 * Extension for the model. Adds a base implementation of the [[Model.State]].
 */
trait SimpleState extends Model {

  override type S = SimpleState

  case class SimpleState(
    actions: Map[String, Action],
    matchState: SimpleMatchState,
    messages: Seq[Message]
  ) extends State

  case class SimpleMatchState(
    player: SimplePlayer,
    rooms: Map[RoomRef, RM],
    items: Map[ItemRef, I],
    ended: Boolean = false
  ) extends MatchState[I, RM]

  case class SimplePlayer(bag: Set[ItemRef], location: RoomRef) extends Player

  /**
   * Companion object with a factory to build the [[State]] with the right constraints of the given
   * extension.
   */
  object State {

    def apply(
      actions: Map[String, Action],
      matchState: SimpleMatchState,
      messages: Seq[Message]
    ): SimpleState = SimpleState(actions, matchState, messages)
  }
}
