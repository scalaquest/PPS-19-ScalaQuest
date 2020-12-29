package io.github.scalaquest.core.model.impl

import io.github.scalaquest.core.model.impl.SimpleModel._

object Behavior {

  /**
   * A sort of plugin that can be added to an item to simulate a specific behaviors.
   */
  trait Behavior {
    def triggers: Triggers
  }

  trait ExtraUtils extends Behavior {
    protected def applyExtraIfPresent(extra: Option[Update])(state: S): S = extra.fold(state)(_(state))
  }

  trait ComposableBehavior extends Behavior {
    def otherBehavior: Behavior
    def baseTrigger: Triggers

    override def triggers: Triggers = Set(baseTrigger, otherBehavior.triggers).reduce(_ orElse _)
  }
}
