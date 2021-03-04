package io.github.scalaquest.core.model.behaviorBased.commons.groundBehaviors.impl

import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.commons.actioning.CActions.Inspect
import io.github.scalaquest.core.model.behaviorBased.commons.pushing.CMessagesExt
import io.github.scalaquest.core.model.behaviorBased.commons.reactions.CReactionsExt
import io.github.scalaquest.core.model.behaviorBased.simple.impl.StateUtilsExt

/**
 * The trait makes possible to mix into a [[BehaviorBasedModel]] the <b>InspectableLocation
 * GroundBehavior</b>.
 */
trait InspectableLocationExt
  extends BehaviorBasedModel
  with CMessagesExt
  with CReactionsExt
  with StateUtilsExt {

  /**
   * A <b>GroundBehavior</b> that enables the possibility to describe the location.
   */
  abstract class InspectableLocation extends GroundBehavior {

    /**
     * A <b>Reaction</b> that communicates to the user a description of the current location.
     * @return
     *   A <b>Reaction</b> that communicates to the user a description of the current location.
     */
    def inspectLocation: Reaction
  }

  /**
   * A standard implementation for <b>InspectableLocation</b>.
   * @param onInspectExtra
   *   <b>Reaction</b> to be executed when the player successfully inspect the location. It can be
   *   omitted.
   */
  case class SimpleInspectableLocation(onInspectExtra: Reaction = Reaction.empty)
    extends InspectableLocation {

    override def triggers: GroundTriggers = { case (Inspect, _) => inspectLocation }

    override def inspectLocation: Reaction =
      for {
        s1 <- Reaction.empty
        _ <- CReactions.addMessage(
          CMessages.Inspected(s1.location, s1.locationItems, s1.location.neighbors(s1))
        )
        s2 <- onInspectExtra
      } yield s2
  }

  /**
   * Companion object for <b>InspectableLocation</b>.
   */
  object InspectableLocation {

    /**
     * Shortcut for the standard implementation.
     * @param onInspectExtra
     *   <b>Reaction</b> to be executed when the player successfully inspect the location. It can be
     *   omitted.
     * @return
     *   the instance of <b>InspectableLocation</b>.
     */
    def apply(onInspectExtra: Reaction = Reaction.empty): InspectableLocation =
      SimpleInspectableLocation(onInspectExtra)
  }
}
