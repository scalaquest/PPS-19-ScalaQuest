package io.github.scalaquest.core.model.behaviorBased.commons.itemBehaviors.impl

import io.github.scalaquest.core.model.Action.Common.Enter
import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.simple.impl.StateUtilsExt

/**
 * The trait makes possible to mix into [[BehaviorBasedModel]] the RoomLink behavior.
 */
trait RoomLinkExt extends BehaviorBasedModel with StateUtilsExt with OpenableExt {

  /**
   * A [[ItemBehavior]] associated to an [[Item]] that enables the possibility to move into another
   * [[Room]]. Conceptually, an [[Item]] that exposes a [[RoomLink]] behavior could also be
   * [[Openable]].
   */
  abstract class RoomLink extends ItemBehavior {
    def isOpen: Boolean
  }

  /**
   * Standard implementation of the RoomLink.
   *
   * This is a behavior associated to an item that is a link between two rooms (a door, for
   * instance), and that could be opened to pass into it. The user can "Enter" into it, resulting
   * into move it into the connected Room. another room.
   * @param endRoom
   *   The room into which the user is projected after entering the object.
   * @param openable
   *   The Openable behavior associated to the roomLink. If not passed, the item can be entered
   *   without opening.
   * @param onEnterExtra
   *   Reaction to be executed into the State when entered, after the standard Reaction. It can be
   *   omitted.
   */
  case class SimpleRoomLink(
    endRoom: Room,
    openable: Option[Openable] = None,
    onEnterExtra: Option[Reaction] = None
  ) extends RoomLink
    with Delegate {

    /**
     * If an openable is passed, it is passed as father behavior.
     * @return
     *   The father behavior triggers.
     */
    override def delegateTriggers: ItemTriggers =
      openable.fold(PartialFunction.empty: ItemTriggers)(_.triggers)

    override def receiverTriggers: ItemTriggers = {
      // "Enter the item"
      case (Enter, item, None, state)
          if state.isInLocation(item) && openable.fold(true)(_.isOpen) =>
        enterRoom()
    }

    def enterRoom(): Reaction =
      state =>
        state.applyReactions(
          locationLens.set(endRoom.ref),
          messageLens.modify(_ :+ Navigated(state.rooms(endRoom.ref))),
          onEnterExtra.getOrElse(state => state)
        )

    override def isOpen: Boolean = openable.fold(true)(_.isOpen)
  }

  /**
   * Companion object for [[RoomLink]]. Shortcut for the standard implementation.
   */
  object RoomLink {

    def apply(endRoom: Room, openable: Option[Openable], onEnterExtra: Option[Reaction]): RoomLink =
      SimpleRoomLink(endRoom, openable, onEnterExtra)
  }
}
