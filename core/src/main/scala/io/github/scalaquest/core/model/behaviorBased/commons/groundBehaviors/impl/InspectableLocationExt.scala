package io.github.scalaquest.core.model.behaviorBased.commons.groundBehaviors.impl

import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.commons.actioning.CommonActions.Inspect
import io.github.scalaquest.core.model.behaviorBased.commons.pushing.CommonMessagesExt
import io.github.scalaquest.core.model.behaviorBased.commons.reactions.CommonReactionsExt

/**
 * The trait makes possible to mix into a [[BehaviorBasedModel]] the InspectableLocation behavior
 * for the Ground.
 */
trait InspectableLocationExt
  extends BehaviorBasedModel
  with CommonMessagesExt
  with CommonReactionsExt {

  /**
   * A [[GroundBehavior]] that enables the possibility to know the items present into the current
   * [[Room]].
   */
  abstract class InspectableLocation extends GroundBehavior {

    /**
     * Inspection of the current player's location.
     * @return
     *   a Reaction with the inspection of current player's location.
     */
    def inspectLocation: Reaction
  }

  /**
   * A standard implementation for [[InspectableLocation]].
   * @param onInspectExtra
   *   [[Reaction]] to be executed when the player successfully inspected the room. It can be
   *   omitted.
   */
  case class SimpleInspectableLocation(onInspectExtra: Reaction = Reaction.empty)
    extends InspectableLocation {

    override def triggers: GroundTriggers = { case (Inspect, _) => inspectLocation }

    override def inspectLocation: Reaction =
      for {
        s1 <- Reaction.empty
        _ <- Reaction.messages(
          Messages.Inspected(s1.location, s1.location.items(s1), s1.location.neighbors(s1))
        )
        s2 <- onInspectExtra
      } yield s2
  }

  /**
   * Companion object for [[InspectableLocation]].
   */
  object InspectableLocation {

    /**
     * Shortcut for the standard implementation.
     * @param onInspectExtra
     *   [[Reaction]] to be executed when the player successfully inspected the room. It can be
     *   omitted.
     * @return
     *   the instance of [[SimpleInspectableLocation]].
     */
    def apply(onInspectExtra: Reaction = Reaction.empty): InspectableLocation =
      SimpleInspectableLocation(onInspectExtra)
  }
}
