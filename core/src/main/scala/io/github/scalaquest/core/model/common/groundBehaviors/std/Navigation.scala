package io.github.scalaquest.core.model.common.groundBehaviors.std

import io.github.scalaquest.core.model.{Direction, Room}
import io.github.scalaquest.core.model.common.Actions.{
  GoDown,
  GoEast,
  GoNorth,
  GoSouth,
  GoUp,
  GoWest
}
import io.github.scalaquest.core.model.common.CommonBase
import monocle.Lens

trait Navigation extends CommonBase {

  case class Navigation(onNavigateExtra: Option[Reaction] = None)(implicit
    currRoomLens: Lens[S, Room]
  ) extends CommonGroundBehaviors.Navigation {

    override def triggers: GroundTriggers = {
      // "go north"
      case (GoNorth, state) if state.location.neighbors(Direction.NORTH).isDefined =>
        movePlayer(state.location.neighbors(Direction.NORTH).get)

      case (GoSouth, state) if state.location.neighbors(Direction.SOUTH).isDefined =>
        movePlayer(state.location.neighbors(Direction.SOUTH).get)

      case (GoEast, state) if state.location.neighbors(Direction.EAST).isDefined =>
        movePlayer(state.location.neighbors(Direction.EAST).get)

      case (GoWest, state) if state.location.neighbors(Direction.WEST).isDefined =>
        movePlayer(state.location.neighbors(Direction.WEST).get)

      case (GoUp, state) if state.location.neighbors(Direction.UP).isDefined =>
        movePlayer(state.location.neighbors(Direction.UP).get)

      case (GoDown, state) if state.location.neighbors(Direction.DOWN).isDefined =>
        movePlayer(state.location.neighbors(Direction.DOWN).get)
    }

    private def movePlayer(targetRoom: Room): Reaction =
      currRoomLens.set(targetRoom)(_).applyReactionIfPresent(onNavigateExtra)

  }
}
