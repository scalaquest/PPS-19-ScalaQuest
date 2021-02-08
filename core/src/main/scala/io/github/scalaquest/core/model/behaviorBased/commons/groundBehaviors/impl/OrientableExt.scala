package io.github.scalaquest.core.model.behaviorBased.commons.groundBehaviors.impl

import io.github.scalaquest.core.model.Action.Common.Orientate
import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.commons.pushing.CommonMessagesExt
import io.github.scalaquest.core.model.behaviorBased.simple.impl.StateUtilsExt

/**
 * The trait makes possible to mix into BehaviorBasedModel the standard implementation of
 * Orientable.
 */
trait OrientableExt extends BehaviorBasedModel with StateUtilsExt with CommonMessagesExt {

  /**
   * A [[GroundBehavior]] that enables the possibility to know the which are the neighbor reachable
   * rooms, and how to reach them.
   */
  abstract class Orientable extends GroundBehavior

  case class SimpleOrientable(onOrientateExtra: Option[Reaction] = None) extends Orientable {

    override def triggers: GroundTriggers = {
      // "orientate"
      case (Orientate, _) => orientate
    }

    def orientate: Reaction =
      state => {
        implicit val s: S = state
        state.applyReactions(
          messageLens.modify(_ :+ Oriented(state.location, state.location.neighbors)),
          onOrientateExtra.getOrElse(s => s)
        )
      }
  }

  object Orientable {

    def apply(onOrientateExtra: Option[Reaction] = None): Orientable =
      SimpleOrientable(onOrientateExtra)
  }
}
