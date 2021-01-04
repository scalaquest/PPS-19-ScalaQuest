package io.github.scalaquest.core.model.impl

import io.github.scalaquest.core.model.impl.Behavior.Behavior
import io.github.scalaquest.core.model.{Action, Message, Model, Room}

/**
 * SimpleModel is a possible implementation of the model, that takes use of the Behavior mechanism.
 * Here you can implement new type definitions
 */
object SimpleModel extends Model {

  override type S = SimpleState
  override type I = BehaviorableItem

  /**
   * Trigger (ex-property) is a proper name, as it is what triggers a reaction,
   * starting from an action. It is a Partial function that makes possible to intercept
   * Actions directed to this item, and process them.
   */
  type TransitiveTriggers   = PartialFunction[(Action, I, S), Reaction]
  type DitransitiveTriggers = PartialFunction[(Action, I, I, S), Reaction]

  /**
   * An item that can have one or more behaviors. Conditions are evaluated and the first one matching
   * is executed.
   */
  trait BehaviorableItem extends Item {
    def behaviors: Set[Behavior] = Set()

    /*
    override def useTransitive[SS <: S](action: Action, state: SS): Option[Reaction] =
      behaviors.map(_.triggers).reduce(_ orElse _).lift((action, this, state))

    override def useDitransitive[SS <: S, II <: I](action: Action, sideItem: II, state: SS): Option[Reaction] =
      behaviors.map(_.ditransitiveTriggers).reduce(_ orElse _).lift((action, this, sideItem, state))
     */

    /*override def useTransitive[SS <: S](
      action: Action,
      state: SS
    ): Option[state.Reaction] = behaviors.map(_.triggers).reduce(_ orElse _).lift((action, this, state))

    override def useDitransitive[SS <: S, II <: I](
      action: Action,
      sideItem: II,
      state: SS
    ): Option[state.Reaction] =
      behaviors.map(_.ditransitiveTriggers).reduce(_ orElse _).lift((action, this, sideItem, state))

     */

    ////// fixme experiments

    /*
    override def useTransitive2[SS <: S](
      action: Action,
      state: SS
    ): Unit = ???

     */
    //override def useTransitive[SS <: Model#S](action: Action, state: SS): Option[state.Reaction] = ???

    //override def useTransitive3(action: Action, state: SimpleState): Option[state.Reaction] =
    //  behaviors.map(_.triggers).reduce(_ orElse _).lift((action, this, state))
    //override def useTransitive3(action: Action, state: SimpleState): Option[state.Reaction] = ???
    // override def useTransitive4(action: Action, state: M#S): Option[M#Reaction] = ???
    override val model: SimpleModel.Self = SimpleModel

    override def useTransitive4(action: Action, state: model.S): Option[model.Reaction] = ???

    ////// fixme end of experiments
  }

  case class SimpleState(game: SimpleGameState, messages: Seq[Message]) extends State

  case class SimpleGameState(player: SimplePlayer, ended: Boolean, rooms: Set[Room], itemsInRooms: Map[Room, Set[I]])
    extends GameState
    with GameStateUtils

  trait GameStateUtils extends GameState {
    def isInBag(item: I): Boolean = this.player.bag.contains(item)

    def isInCurrentRoom(item: I): Boolean =
      this.itemsInRooms.collectFirst({ case (room, items) if items.contains(item) => room }).isDefined
  }

  case class SimplePlayer(bag: Set[I], location: Room) extends Player
}
