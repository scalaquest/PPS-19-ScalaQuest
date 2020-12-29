package io.github.scalaquest.core.model.impl

import io.github.scalaquest.core.model.impl.Behavior.Behavior
import io.github.scalaquest.core.model.{Action, Message, Model, Room}

/**
 * SimpleModel is a possible implementation of the model, that takes use of the Behavior mechanism.
 * Here you can implement new type definitions
 */
object SimpleModel extends Model {

  /**
   * Trigger (ex-property) is a proper name, as it is what triggers a reaction,
   * starting from an action. It is a Partial function that makes possible to intercept
   * Actions directed to this item, and process them.
   */
  type Triggers = PartialFunction[(Action, Item, S), Update]

  override type S = SimpleState
  override type I = Item

  /**
   * An item that can have one or more behaviors. Conditions are evaluated and the first one matching
   * is executed.
   */
  trait BehaviorableItem extends I {
    def behaviors: Set[Behavior] = Set()

    override def use(action: Action, state: S): Option[Update] =
      behaviors.map(_.triggers).reduce(_ orElse _).lift((action, this, state))
  }

  case class SimplePlayer(bag: Set[I], location: Room) extends Player

  trait GameStateUtils extends GameState {
    def isInBag(item: I): Boolean = this.player.bag.contains(item)

    def isInCurrentRoom(item: I): Boolean =
      this.itemsInRooms.collectFirst({ case (room, items) if items.contains(item) => room }).isDefined
  }

  case class SimpleGameState(player: SimplePlayer, ended: Boolean) extends GameState with GameStateUtils {
    override def rooms: Set[Room]                = ???
    override def itemsInRooms: Map[Room, Set[I]] = ???
  }

  case class SimpleState(game: SimpleGameState, messages: Seq[Message]) extends State

}
