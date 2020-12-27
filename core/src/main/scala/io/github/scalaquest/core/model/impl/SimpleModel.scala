package io.github.scalaquest.core.model.impl

import io.github.scalaquest.core.model.common.Actions._
import io.github.scalaquest.core.model.{Action, Message, Model, Room}

// Here you can implement new type definitions
object SimpleModel extends Model {

  override type S = State
  override type I = Item

  case class SimpleItem(name: String) extends Item with Takeable {

    // valutare se l'azione è contenuta nel set delle azioni consentite
    // return il corrispondente update
    override def use(action: Action, state: S): Option[Update] =
      properties.reduce(_ orElse _).lift((action, this, state))
  }

  trait Takeable extends Item {
    val isOpen: Boolean = false

    val takeObject: Update = state => {
      // togli l'oggetto corrente dalla room, mettilo nella bag
      state
    }

    val checkTakeable: Property = {
      case (Take, _, _) => takeObject // controlla se l'oggetto è nella room
    }

    properties += checkTakeable

  }

  case class SimplePlayer(bag: Set[Item], location: Room) extends Player

  case class SimpleGameState(player: SimplePlayer, ended: Boolean) extends GameState

  case class SimpleState(game: SimpleGameState, messages: Seq[Message]) extends State
}
