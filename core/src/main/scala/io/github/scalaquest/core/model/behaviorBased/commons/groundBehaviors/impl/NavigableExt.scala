package io.github.scalaquest.core.model.behaviorBased.commons.groundBehaviors.impl

import io.github.scalaquest.core.model.Direction
import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.commons.actioning.CActions.Go
import io.github.scalaquest.core.model.behaviorBased.commons.pushing.CMessagesExt
import io.github.scalaquest.core.model.behaviorBased.commons.reactions.CReactionsExt
import io.github.scalaquest.core.model.behaviorBased.simple.impl.StateUtilsExt

/**
 * The trait makes possible to mix into a [[BehaviorBasedModel]] the <b>Navigable
 * GroundBehavior</b>.
 */
trait NavigableExt
  extends BehaviorBasedModel
  with CMessagesExt
  with CReactionsExt
  with StateUtilsExt {

  /**
   * A <b>GroundBehavior</b> that enables the possibility to change the location using
   * <b>Directions<b>.
   */
  abstract class Navigable extends GroundBehavior {

    /**
     * Sets the location of the player to the given <b>Room</b>.
     * @param targetRoom
     *   The <b>Room</b> into which move the player.
     * @return
     *   A <b>Reaction</b> that sets the location of the player to the given <b>Room</b>.
     */
    def movePlayer(targetRoom: RM): Reaction
  }

  /**
   * Standard implementation of <b>Navigable</b>.
   *
   * @param onNavigateExtra
   *   <b>Reaction</b> to be executed when the player successfully navigate in a new <b>Room</b>. It
   *   can be omitted.
   */
  case class SimpleNavigable(onNavigateExtra: Reaction = Reaction.empty) extends Navigable {

    override def triggers: GroundTriggers = { case (Go(d), s) =>
      s.locationNeighbor(d) map movePlayer getOrElse failedToNavigate(d)
    }

    override def movePlayer(targetRoom: RM): Reaction =
      for {
        _ <- CReactions.switchLocation(targetRoom)
        _ <- Reaction.messages(CMessages.Navigated(targetRoom))
        s <- onNavigateExtra
      } yield s

    def failedToNavigate(direction: Direction): Reaction =
      Reaction.appendMessage(CMessages.FailedToNavigate(direction))(Reaction.empty)
  }

  /**
   * Companion object for <b>Navigable</b>.
   */
  object Navigable {

    /**
     * Shortcut for the standard implementation.
     * @param onNavigateExtra
     *   <b>Reaction<b> to be executed when the player successfully navigate in a new <b>Room</b>.
     *   It can be omitted.
     * @return
     *   The instance of <b>Navigable</b>.
     */
    def apply(onNavigateExtra: Reaction = Reaction.empty): Navigable =
      SimpleNavigable(onNavigateExtra)
  }
}
