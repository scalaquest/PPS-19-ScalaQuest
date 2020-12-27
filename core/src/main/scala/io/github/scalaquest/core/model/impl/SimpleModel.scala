package io.github.scalaquest.core.model.impl

import io.github.scalaquest.core.model.common.Actions._
import io.github.scalaquest.core.model.{Action, Message, Model, Room}

// Here you can implement new type definitions
object SimpleModel extends Model {

  override type S = SimpleState
  override type I = SimpleItem
  type Property   = PartialFunction[(Action, Item, S), Update]

  case class SimpleItem(name: String) extends Item {
    var properties: Set[Property] = Set()

    // valutare se l'azione è contenuta nel set delle azioni consentite
    // return il corrispondente update
    override def use(action: Action, state: S): Option[Update] =
      properties.reduce(_ orElse _).lift((action, this, state))

  }

  trait Takeable extends SimpleItem {
    val isOpen: Boolean = false

    val takeObject: Update = state => {
      // togli l'oggetto corrente dalla room, mettilo nella bag
      state
    }

    val checkTakeable: Property = {
      case (Take, _, _) => takeObject // controlla se l'oggetto è nella room
    }

    super.properties = super.properties + checkTakeable

  }

  case class SimplePlayer(bag: Set[SimpleItem], location: Room) extends Player

  case class SimpleGameState(player: SimplePlayer, ended: Boolean) extends GameState

  case class SimpleState(game: SimpleGameState, messages: Seq[Message]) extends State
}
