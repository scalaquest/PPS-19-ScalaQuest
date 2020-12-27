package io.github.scalaquest.core.model.impl

import io.github.scalaquest.core.model.{Action, Message, Model, Room}

// Here you can implement new type definitions
object SimpleModel extends Model {

  override type S = SimpleState
  override type I = SimpleItem

  case class SimpleItem(name: String) extends Item {
    override def use(action: Action, state: S): Option[Update] = ??? //properties.reduce()
  }

  case class SimplePlayer(bag: Set[SimpleItem], location: Room) extends Player

  case class SimpleGameState(player: SimplePlayer, ended: Boolean) extends GameState

  case class SimpleState(game: SimpleGameState, messages: Seq[Message]) extends State
}
