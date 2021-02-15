package io.github.scalaquest.core.model.behaviorBased.commons.itemBehaviors.impl

import io.github.scalaquest.core.model.Direction
import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.commons.actioning.CommonActions.{Enter, Open}
import io.github.scalaquest.core.model.behaviorBased.commons.reactions.CommonReactionsExt
import io.github.scalaquest.core.model.behaviorBased.simple.impl.StateUtilsExt

/**
 * The trait makes possible to mix into [[BehaviorBasedModel]] the RoomLink behavior.
 */
trait RoomLinkExt
  extends BehaviorBasedModel
  with StateUtilsExt
  with OpenableExt
  with CommonReactionsExt {

  /**
   * A [[ItemBehavior]] associated to an [[Item]] that enables the possibility to move into another
   * [[Room]]. Conceptually, an [[Item]] that exposes a [[RoomLink]] behavior could also be
   * [[Openable]].
   */
  abstract class RoomLink extends ItemBehavior {
    def isOpen: Boolean
    def openable: Option[Openable]
    def endRoom: RM
    def enter: Reaction
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
    endRoom: RM,
    endRoomDirection: Direction,
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
      // "Open the item (with something)"
      case (Open, i, maybeKey, s)
          if s.isInLocation(i) && openable.fold(true)(_.canBeOpened(maybeKey)(s))
            && !isOpen && openable.isDefined =>
        open

      // "Enter the item"
      case (Enter, i, None, s) if s.isInLocation(i) && openable.fold(true)(_.isOpen) => enter
    }

    override def enter: Reaction =
      _.applyReactions(
        Reactions.enter(endRoom),
        onEnterExtra.getOrElse(Reactions.empty)
      )

    def open: Reaction =
      state => {
        val addDirection = roomsLens.modify(
          _.updatedWith(state.location.ref) {
            case Some(room) =>
              Some(roomDirectionsLens.modify(_ + (endRoomDirection -> endRoom.ref))(room))
            case _ => None
          }
        )

        state.applyReactions(
          openable.fold(Reactions.empty)(o => o.open),
          addDirection
        )
      }

    override def isOpen: Boolean = openable.fold(true)(_.isOpen)
  }

  /**
   * Companion object for [[RoomLink]]. Shortcut for the standard implementation.
   */
  object RoomLink {

    def builder(
      endRoom: RM,
      endRoomDirection: Direction,
      openableBuilder: Option[I => Openable] = None,
      onEnterExtra: Option[Reaction] = None
    ): I => RoomLink =
      item => SimpleRoomLink(endRoom, endRoomDirection, openableBuilder.map(_(item)), onEnterExtra)
  }
}
