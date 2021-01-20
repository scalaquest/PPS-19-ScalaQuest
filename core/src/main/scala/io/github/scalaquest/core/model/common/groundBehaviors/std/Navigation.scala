package io.github.scalaquest.core.model.common.groundBehaviors.std

import io.github.scalaquest.core.model.{Room}
import io.github.scalaquest.core.model.common.Actions.Go

import io.github.scalaquest.core.model.common.CommonBase
import monocle.Lens

trait Navigation extends CommonBase {

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
