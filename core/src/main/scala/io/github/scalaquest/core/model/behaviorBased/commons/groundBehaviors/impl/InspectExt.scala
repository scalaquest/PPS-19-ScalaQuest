package io.github.scalaquest.core.model.behaviorBased.commons.groundBehaviors.impl

import io.github.scalaquest.core.model.Action.Common.Inspect
import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.commons.pushing.CommonMessagesExt
import io.github.scalaquest.core.model.behaviorBased.simple.impl.StateUtilsExt

/**
 * The trait makes possible to mix into a [[BehaviorBasedModel]] the Inspectable behavior for the
 * Ground.
 */
trait InspectExt extends BehaviorBasedModel with StateUtilsExt with CommonMessagesExt {

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

    override def triggers: GroundTriggers = { case (Inspect, state) =>
      inspectRoom(state.location)
    }

    def inspectRoom(targetRoom: RM): Reaction =
      state =>
        state.applyReactions(
          messageLens.modify(_ :+ Inspected(targetRoom, targetRoom.items(state))),
          onInspectExtra.getOrElse(s => s)
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
