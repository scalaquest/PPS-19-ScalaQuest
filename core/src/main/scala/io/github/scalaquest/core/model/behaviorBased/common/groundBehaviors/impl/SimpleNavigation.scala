package io.github.scalaquest.core.model.behaviorBased.common.groundBehaviors.impl

import io.github.scalaquest.core.model.Action.Common.Go
import io.github.scalaquest.core.model.Room
import io.github.scalaquest.core.model.behaviorBased.common.CommonBase
import monocle.Lens

/**
 * The trait makes possible to mix into StdCommonGroundBehaviors the standard implementation of
 * Navigation.
 */
trait SimpleNavigation extends CommonBase {

  /**
   * Standard implementation of the Navigation GroundBehavior.
   * @param onNavigateExtra
   *   Reaction to be executed when the player successfully navigate in a new Room, using navigation
   *   Actions after the standard [[Reaction]]. It can be omitted.
   */
  case class SimpleNavigation(onNavigateExtra: Option[Reaction] = None)(implicit
    currRoomLens: Lens[S, Room]
  ) extends Navigation {

    override def triggers: GroundTriggers = {
      // "go <direction>"
      case (Go(direction), state) if state.location.neighbors(direction).isDefined =>
        movePlayer(state.location.neighbors(direction).get)
    }

    private def movePlayer(targetRoom: Room): Reaction =
      currRoomLens.set(targetRoom)(_).applyReactionIfPresent(onNavigateExtra)
  }
}
