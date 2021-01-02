package io.github.scalaquest.core.model.impl

import io.github.scalaquest.core.model.{Action, Message, Model, Room}

object SimpleModel extends Model {
  override type S = SimpleState
  override type I = SimpleItem

  trait SimpleItem extends Item {
    override def useTransitive[SS <: Model#S, UU <: Model#Update](action: Action, state: SS): Option[UU] = None

    override def useDitransitive[SS <: Model#S, II <: Model#I, UU <: Model#Update](
      action: Action,
      sideItem: II,
      state: SS
    ): Option[UU] = None
  }

  case class SimplePlayer(bag: Set[SimpleItem], location: Room) extends Player

  case class SimpleGameState(player: SimplePlayer, ended: Boolean) extends GameState

  case class SimpleState(game: SimpleGameState, messages: Seq[Message]) extends State
}
