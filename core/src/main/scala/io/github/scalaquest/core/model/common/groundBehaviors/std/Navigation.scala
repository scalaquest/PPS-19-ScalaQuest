package io.github.scalaquest.core.model.common.groundBehaviors.std

import io.github.scalaquest.core.model.Room
import io.github.scalaquest.core.model.common.Actions.Go

import io.github.scalaquest.core.model.common.CommonBase
import monocle.Lens

/**
 * The trait makes possible to mix into StdCommonGroundBehaviors the standard implementation of
 * Navigation.
 */
trait Navigation extends CommonBase {

  /**
   * Standard implementation of the Navigation GroundBehavior.
   * @param onNavigateExtra
   *   Reaction to be executed when the player successfully navigate in a new Room, using navigation
   *   Actions after the standard [[Reaction]]. It can be omitted.
   */
  case class Navigation(onNavigateExtra: Option[Reaction] = None)(implicit
    currRoomLens: Lens[S, Room]
  ) extends CommonGroundBehaviors.Navigation {

    override def triggers: GroundTriggers = {
      // "go <direction>"
      case (Go(direction), state) if state.location.neighbors(direction).isDefined =>
        movePlayer(state.location.neighbors(direction).get)
    }

    private def movePlayer(targetRoom: Room): Reaction =
      currRoomLens.set(targetRoom)(_).applyReactionIfPresent(onNavigateExtra)
  }
}
