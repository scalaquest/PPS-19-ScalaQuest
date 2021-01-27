package io.github.scalaquest.core.model.impl

import io.github.scalaquest.core.model._

/**
 * This can be used as a mixin or as an extension for the model. Adds a simple implementation of the
 * State into the model.
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

  object State {

    def apply(
      actions: Map[String, Action],
      matchState: SimpleMatchState,
      messages: Seq[Message]
    ): SimpleState = SimpleState(actions, matchState, messages)
  }
}
