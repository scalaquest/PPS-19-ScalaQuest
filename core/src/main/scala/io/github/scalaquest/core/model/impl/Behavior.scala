package io.github.scalaquest.core.model.impl

import io.github.scalaquest.core.model.impl.SimpleModel._

object Behavior {

  /**
   * A sort of plugin that can be added to an item to simulate a specific behaviors.
   */
  trait Behavior {
    def triggers: TransitiveTriggers               = PartialFunction.empty
    def ditransitiveTriggers: DitransitiveTriggers = PartialFunction.empty
  }

  trait ExtraUtils extends Behavior {
    protected def applyExtraIfPresent(extra: Option[Update])(state: S): S = extra.fold(state)(_(state))
  }

  trait ComposableBehavior extends Behavior {
    def superBehavior: Behavior
    def baseTrigger: TransitiveTriggers                = PartialFunction.empty
    def baseDitransitiveTriggers: DitransitiveTriggers = PartialFunction.empty

    override def triggers: TransitiveTriggers = Seq(baseTrigger, superBehavior.triggers).reduce(_ orElse _)

    override def ditransitiveTriggers: DitransitiveTriggers =
      Seq(baseDitransitiveTriggers, superBehavior.ditransitiveTriggers).reduce(_ orElse _)
  }
}
