package io.github.scalaquest.core.model.behaviorBased.common.groundBehaviors.impl

import io.github.scalaquest.core.model.Action.Common.Go
import io.github.scalaquest.core.model.RoomRef
import io.github.scalaquest.core.model.behaviorBased.common.CommonBase
import monocle.Lens

/**
 * The trait makes possible to mix into StdCommonGroundBehaviors the standard implementation of
 * Navigation.
 */
trait SimpleNavigationExt extends CommonBase {

  /**
   * Standard implementation of the Navigation GroundBehavior.
   * @param onNavigateExtra
   *   Reaction to be executed when the player successfully navigate in a new Room, using navigation
   *   Actions after the standard [[Reaction]]. It can be omitted.
   */
  case class SimpleNavigation(onNavigateExtra: Option[Reaction] = None)(implicit
    playerLocationLens: Lens[S, RoomRef]
  ) extends Navigation {

    override def triggers: GroundTriggers = {
      // "go <direction>"
      case (Go(direction), state) if state.currentRoom.neighbor(direction).isDefined =>
        movePlayer(state.currentRoom.neighbor(direction).get)
    }

    private def movePlayer(targetRoomRef: RoomRef): Reaction =
      playerLocationLens.set(targetRoomRef)(_).applyReactionIfPresent(onNavigateExtra)
  }
}
