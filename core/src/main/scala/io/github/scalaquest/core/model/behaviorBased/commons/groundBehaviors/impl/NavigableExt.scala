package io.github.scalaquest.core.model.behaviorBased.commons.groundBehaviors.impl

import io.github.scalaquest.core.model.Direction
import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.commons.actioning.CommonActions.Go
import io.github.scalaquest.core.model.behaviorBased.commons.pushing.CommonMessagesExt
import io.github.scalaquest.core.model.behaviorBased.commons.reactions.CommonReactionsExt

/**
 * The trait makes possible to mix into a [[BehaviorBasedModel]] the Navigable behavior for the
 * [[BehaviorBasedModel.Ground]].
 */
trait NavigableExt extends BehaviorBasedModel with CommonMessagesExt with CommonReactionsExt {

  /**
   * A [[GroundBehavior]] that enables the possibility to navigate Rooms using Directions.
   */
  abstract class Navigable extends GroundBehavior {

    /**
     * Move the player into a selected Room [[RM]].
     * @param targetRoom
     *   the selected room.
     * @return
     *   the Reaction that move the player into the targetRoom.
     */
    def movePlayer(targetRoom: RM): Reaction
  }

  /**
   * Standard implementation of [[Navigable]].
   *
   * @param onNavigateExtra
   *   [[Reaction]] to be executed when the player successfully navigate in a new Room, using
   *   navigation Actions after the standard [[Reaction]]. It can be omitted.
   */
  case class SimpleNavigable(onNavigateExtra: Reaction = Reaction.empty) extends Navigable {

    override def triggers: GroundTriggers = { case (Go(d), s) =>
      s.locationNeighbor(d) map movePlayer getOrElse failedToNavigate(d)
    }

    override def movePlayer(targetRoom: RM): Reaction =
      Reaction.combine(
        Reactions.navigate(targetRoom),
        onNavigateExtra
      )

    def failedToNavigate(direction: Direction): Reaction =
      Reaction.appendMessage(Messages.FailedToNavigate(direction))(Reaction.empty)
  }

  /**
   * Companion object for [[Navigable]]. Shortcut for the standard implementation.
   */
  object Navigable {

    /**
     * Create a Navigable [[GroundBehavior]].
     * @param onNavigateExtra
     *   [[Reaction]] to be executed when the player successfully navigate in a new Room, using
     *   navigation Actions after the standard [[Reaction]]. It can be omitted.
     * @return
     *   the instance of [[SimpleNavigable]].
     */
    def apply(onNavigateExtra: Reaction = Reaction.empty): Navigable =
      SimpleNavigable(onNavigateExtra)
  }
}
