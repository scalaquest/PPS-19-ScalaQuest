package io.github.scalaquest.core.model.std

import io.github.scalaquest.core.model.{GameState, Message, Model, Player, Room}

/**
 * This can be used as a mixin or as an extension for the model. Adds a simple implementation of the
 * State into the model.
 */
trait StdState extends Model {

  override type S = StdState

  case class StdState(game: StdGameState, messages: Seq[Message]) extends State

  case class StdGameState(
    player: StdPlayer,
    ended: Boolean,
    rooms: Set[Room],
    itemsInRooms: Map[Room, Set[I]]
  ) extends GameState[I]

  case class StdPlayer(bag: Set[I], location: Room) extends Player[I]
}
