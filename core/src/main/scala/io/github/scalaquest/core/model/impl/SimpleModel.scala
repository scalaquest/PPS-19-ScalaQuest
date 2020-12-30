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
  type TransitiveTriggers   = PartialFunction[(Action, I, S), Update]
  type DitransitiveTriggers = PartialFunction[(Action, I, I, S), Update]

  override type S = SimpleState
  override type I = BehaviorableItem

  /**
   * An item that can have one or more behaviors. Conditions are evaluated and the first one matching
   * is executed.
   */
  trait BehaviorableItem extends Item {
    def behaviors: Set[Behavior] = Set()

    override def useTransitive(action: Action, state: S): Option[Update] =
      behaviors.map(_.triggers).reduce(_ orElse _).lift((action, this, state))

    override def useDitransitive(action: Action, sideItem: I, state: S): Option[Update] =
      behaviors.map(_.ditransitiveTriggers).reduce(_ orElse _).lift((action, this, sideItem, state))

  }

  case class SimplePlayer(bag: Set[I], location: Room) extends Player

  trait GameStateUtils extends GameState {
    def isInBag(item: I): Boolean = this.player.bag.contains(item)

    def isInCurrentRoom(item: I): Boolean =
      this.itemsInRooms.collectFirst({ case (room, items) if items.contains(item) => room }).isDefined
  }

  case class SimpleGameState(player: SimplePlayer, ended: Boolean, rooms: Set[Room], itemsInRooms: Map[Room, Set[I]])
    extends GameState
    with GameStateUtils {}

  case class SimpleState(game: SimpleGameState, messages: Seq[Message]) extends State

}
