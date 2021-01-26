package io.github.scalaquest.core.model.behaviorBased.impl

import io.github.scalaquest.core.model.{
  Action,
  ItemRef,
  MatchState,
  Message,
  Model,
  Player,
  RoomRef
}

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
    ended: Boolean,
    rooms: Map[RoomRef, RM],
    items: Map[ItemRef, I]
  ) extends MatchState[I, RM]

  case class SimplePlayer(bag: Set[ItemRef], location: RoomRef) extends Player
}
