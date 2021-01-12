package io.github.scalaquest.core.model.common.behaviors.std

import io.github.scalaquest.core.model.Room
import io.github.scalaquest.core.model.common.Actions.Enter
import io.github.scalaquest.core.model.common.behaviors.{CommonBehaviors, StdCommonBehaviors, StdCommonBehaviorsBase}
import monocle.Lens

/**
 * The trait makes possible to mix into the [[StdCommonBehaviors]] the standard implementation of
 * [[CommonBehaviors.CommonBehaviors.RoomLink]].
 */
private[behaviors] trait RoomLink extends StdCommonBehaviorsBase {

  /**
   * Standard implementation of the [[CommonBehaviors.RoomLink]].
   *
   * This is a behavior associated to an item that is a link between two rooms (a door, for instance), and that could be
   * opened to pass into it. The user can "Enter" into it, resulting into move it into the connected Room.
   * another room.
   * @param endRoom The room into which the user is projected after entering the object.
   * @param openable The Openable behavior associated to the roomLink. If not passed, the item can be entered without
   *                 opening.
   * @param onEnterExtra Reaction to be executed into the State when entered, after the standard Reaction.
   *                     It can be omitted.
   */
  case class RoomLink(
    endRoom: Room,
    openable: Option[CommonBehaviors.Openable] = None,
    onEnterExtra: Option[Reaction] = None
  )(
    implicit currRoomLens: Lens[S, Room]
  ) extends CommonBehaviors.RoomLink
    with Composable {

    /**
     * If an openable is passed, it is passed as father behavior.
     *  @return The father behavior triggers.
     */
    override def superTriggers: Triggers = openable.fold(PartialFunction.empty: Triggers)(_.triggers)

    override def baseTriggers: Triggers = {
      // "Enter the item"
      case (Enter, _, None, _) if openable.fold(true)(_.isOpen) => enterRoom()
    }

    def enterRoom(): Reaction =
      state => {
        val updLocState = currRoomLens.modify(_ => endRoom)(state)
        updLocState.applyReactionIfPresent(onEnterExtra)
      }
  }
}
