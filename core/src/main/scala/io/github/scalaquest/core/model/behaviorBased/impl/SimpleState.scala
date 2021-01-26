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

  final case class SimpleState(
    actions: Map[String, Action],
    matchState: SimpleMatchState,
    messages: Seq[Message]
  ) extends State

  final case class SimpleMatchState(
    player: SimplePlayer,
    ended: Boolean,
    rooms: Set[RM],
    items: Set[I]
  ) extends MatchState[I, RM]

  final case class SimplePlayer(bag: Set[ItemRef], location: RoomRef) extends Player
}
