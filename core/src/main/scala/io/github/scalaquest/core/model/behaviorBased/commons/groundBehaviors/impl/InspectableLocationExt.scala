package io.github.scalaquest.core.model.behaviorBased.commons.groundBehaviors.impl

import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.commons.actioning.CommonActions.Inspect
import io.github.scalaquest.core.model.behaviorBased.commons.pushing.CommonMessagesExt
import io.github.scalaquest.core.model.behaviorBased.commons.reactions.CommonReactionsExt
import io.github.scalaquest.core.model.behaviorBased.simple.impl.StateUtilsExt

/**
 * The trait makes possible to mix into a [[BehaviorBasedModel]] the InspectableLocation behavior
 * for the Ground.
 */
trait InspectableLocationExt
  extends BehaviorBasedModel
  with StateUtilsExt
  with CommonMessagesExt
  with CommonReactionsExt {

  /**
   * A [[GroundBehavior]] that enables the possibility to know the items present into the current
   * [[Room]].
   */
  abstract class InspectableLocation extends GroundBehavior

  /**
   * A standard implementation for [[InspectableLocation]].
   *
   * @param onInspectExtra
   *   [[Reaction]] to be executed when the player successfully inspected the room. It can be
   *   omitted.
   */
  case class SimpleInspectableLocation(onInspectExtra: Option[Reaction] = None)
    extends InspectableLocation {

    override def triggers: GroundTriggers = { case (Inspect, _) => inspectLocation }

    private def inspectLocation: Reaction =
      _.applyReactions(
        Reactions.inspectLocation,
        onInspectExtra.getOrElse(Reactions.empty)
      )
  }

  /**
   * Companion object for [[InspectableLocation]]. Shortcut for the standard implementation.
   */
  object InspectableLocation {

    def apply(onInspectExtra: Option[Reaction] = None): InspectableLocation =
      SimpleInspectableLocation(onInspectExtra)
  }

}
