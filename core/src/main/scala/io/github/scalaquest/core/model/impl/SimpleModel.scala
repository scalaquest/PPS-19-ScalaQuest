package io.github.scalaquest.core.model.impl

import io.github.scalaquest.core.model.{Action, Message, Model, Room}

// Here you can implement new type definitions
object SimpleModel extends Model {

  override type S = SimpleState
  override type I = SimpleItem

  case class SimpleItem(
      name: String
  ) extends Item {
    override def use(action: Action): Option[Effect] = Some(x => x)
  }

  case class SimplePlayer(
      bag: Set[SimpleItem],
      location: Room
  ) extends Player

  case class SimpleGame(
      player: SimplePlayer,
      ended: Boolean
  ) extends Game

  case class SimpleState(
      game: SimpleGame,
      messages: Seq[Message]
  ) extends State
}
