package io.github.scalaquest.core.model.default

import io.github.scalaquest.core.model.{GameState, Message, Model, Player, Room}

/**
 * This can be used as a mixin or as an extension for the model. Adds a simple implementation of the State into
 * the model.
 */
trait DefaultState extends Model {

  override type S = DefaultState

  case class DefaultState(game: DefaultGameState = ???, messages: Seq[Message] = ???) extends State

  case class DefaultGameState(
    player: DefaultPlayer = ???,
    ended: Boolean = ???,
    rooms: Set[Room] = ???,
    itemsInRooms: Map[Room, Set[I]] = ???
  ) extends GameState[I]

  case class DefaultPlayer(bag: Set[I] = ???, location: Room = ???) extends Player[I]
}
