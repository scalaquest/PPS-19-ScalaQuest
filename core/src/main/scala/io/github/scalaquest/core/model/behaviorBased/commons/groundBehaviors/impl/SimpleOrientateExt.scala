package io.github.scalaquest.core.model.behaviorBased.commons.groundBehaviors.impl

import io.github.scalaquest.core.model.Action.Common.Orientate
import io.github.scalaquest.core.model.behaviorBased.commons.CommonBase

/**
 * The trait makes possible to mix into StdCommonGroundBehaviors the standard implementation of
 * Navigation.
 */
trait SimpleOrientateExt extends CommonBase {

  case class SimpleOrientate(onOrientateExtra: Option[Reaction] = None) extends Orientate {

    override def triggers: GroundTriggers = {
      // "orientate"
      case (Orientate, state) => orientate
    }

    def orientate: Reaction =
      state =>
        state.applyReactions(
          messageLens.modify(
            _ :+ Oriented(
              state.location,
              state.location.neighbors(state)
            )
          ),
          onOrientateExtra.getOrElse(s => s)
        )
  }
}
