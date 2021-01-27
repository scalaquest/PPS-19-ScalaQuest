package io.github.scalaquest.core.model.behaviorBased.common.itemBehaviors.impl

import io.github.scalaquest.core.model.Action.Common.Enter
import io.github.scalaquest.core.model.{Message, RoomRef}
import io.github.scalaquest.core.model.behaviorBased.common.CommonBase
import monocle.Lens

/**
 * The trait makes possible to mix into StdCommonBehaviors the standard implementation of RoomLink.
 */
trait SimpleRoomLinkExt extends CommonBase {

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
    openable: Option[Openable] = None,
    onEnterExtra: Option[Reaction] = None
  )(implicit
    playerLocationLens: Lens[S, RoomRef],
    messageLens: Lens[S, Seq[Message]]
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
          if state.isInCurrentRoom(item) && openable.fold(true)(_.isOpen) =>
        enterRoom()
    }

    def enterRoom(): Reaction =
      _.applyReactions(
        playerLocationLens.modify(_ => endRoom.ref),
        messageLens.modify(_ :+ Navigated(endRoom)),
        onEnterExtra.getOrElse(state => state)
      )

    override def isAccessible: Boolean = openable.fold(true)(_.isOpen)
  }
}
