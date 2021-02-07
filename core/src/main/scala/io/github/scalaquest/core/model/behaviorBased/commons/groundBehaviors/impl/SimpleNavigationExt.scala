package io.github.scalaquest.core.model.behaviorBased.commons.groundBehaviors.impl

import io.github.scalaquest.core.model.Action.Common.Go
import io.github.scalaquest.core.model.{Message, RoomRef}
import io.github.scalaquest.core.model.behaviorBased.commons.CommonBase
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
  case class SimpleNavigation(onNavigateExtra: Option[Reaction] = None) extends Navigation {

    override def triggers: GroundTriggers = {
      // "go <direction>"
      case (Go(direction), state) if state.locationNeighbor(direction).isDefined =>
        movePlayer(state.locationNeighbor(direction).get)
    }

    def movePlayer(targetRoom: RM): Reaction =
      state => {
        state.applyReactions(
          locationLens.set(targetRoom.ref),
          messageLens.modify(_ :+ Navigated(targetRoom)),
          onNavigateExtra.getOrElse(s => s)
        )
      }
  }
}
