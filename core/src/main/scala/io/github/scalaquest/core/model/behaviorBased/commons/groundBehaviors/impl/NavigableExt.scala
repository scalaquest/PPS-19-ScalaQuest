package io.github.scalaquest.core.model.behaviorBased.commons.groundBehaviors.impl

import io.github.scalaquest.core.model.Action.Common.Go
import io.github.scalaquest.core.model.Message
import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.commons.pushing.CommonMessagesExt
import io.github.scalaquest.core.model.behaviorBased.simple.impl.StateUtilsExt

/**
 * The trait makes possible to mix into a [[BehaviorBasedModel]] the Navigatable behavior for the
 * [[BehaviorBasedModel.Ground]].
 */
trait NavigableExt extends BehaviorBasedModel with StateUtilsExt with CommonMessagesExt {

  /**
   * A [[GroundBehavior]] that enables the possibility to navigate Rooms using Directions.
   */
  abstract class Navigable extends GroundBehavior

  /**
   * Standard implementation of [[Navigable]].
   *
   * @param onNavigateExtra
   *   [[Reaction]] to be executed when the player successfully navigate in a new Room, using
   *   navigation Actions after the standard [[Reaction]]. It can be omitted.
   */
  case class SimpleNavigable(onNavigateExtra: Option[Reaction] = None) extends Navigable {

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

  /**
   * Companion object for [[Navigable]]. Shortcut for the standard implementation.
   */
  object Navigable {

    def apply(onNavigateExtra: Option[Reaction] = None): Navigable =
      SimpleNavigable(onNavigateExtra)
  }
}
