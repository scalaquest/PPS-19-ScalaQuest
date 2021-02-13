package io.github.scalaquest.core.model.behaviorBased.commons.groundBehaviors.impl

import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.commons.actioning.CommonActions.Inspect
import io.github.scalaquest.core.model.behaviorBased.commons.pushing.CommonMessagesExt
import io.github.scalaquest.core.model.behaviorBased.commons.reactions.CommonReactionsExt
import io.github.scalaquest.core.model.behaviorBased.simple.impl.StateUtilsExt

/**
 * The trait makes possible to mix into a [[BehaviorBasedModel]] the Inspectable behavior for the
 * Ground.
 */
trait InspectableExt
  extends BehaviorBasedModel
  with StateUtilsExt
  with CommonMessagesExt
  with CommonReactionsExt {

  /**
   * A [[GroundBehavior]] that enables the possibility to know the items present into the current
   * Room.
   */
  abstract class Inspectable extends GroundBehavior

  /**
   * A standard implementation for [[Inspectable]].
   * @param onInspectExtra
   *   [[Reaction]] to be executed when the player succdessfully inspected the room. It can be
   *   omitted.
   */
  case class SimpleInspectable(onInspectExtra: Option[Reaction] = None) extends Inspectable {

    override def triggers: GroundTriggers = { case (Inspect, _) => inspectLocation }

    def inspectLocation: Reaction =
      _.applyReactions(
        Reactions.inspectLocation,
        onInspectExtra.getOrElse(Reactions.empty)
      )
  }

  /**
   * Companion object for [[Inspectable]]. Shortcut for the standard implementation.
   */
  object Inspectable {

    def apply(onInspectExtra: Option[Reaction] = None): Inspectable =
      SimpleInspectable(onInspectExtra)
  }

}
