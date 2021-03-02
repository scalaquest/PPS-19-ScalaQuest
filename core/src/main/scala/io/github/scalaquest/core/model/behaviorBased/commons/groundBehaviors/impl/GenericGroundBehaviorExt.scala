package io.github.scalaquest.core.model.behaviorBased.commons.groundBehaviors.impl

import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.commons.pushing.CMessagesExt
import io.github.scalaquest.core.model.behaviorBased.commons.reactions.CReactionsExt

/**
 * The trait makes possible to mix into a [[BehaviorBasedModel]] the <b>GenericGroundBehavior
 * GroundBehavior</b>.
 */
trait GenericGroundBehaviorExt extends BehaviorBasedModel with CMessagesExt with CReactionsExt {

  /**
   * A GroundBehavior with some triggers. It is useful in order to wrap <b>GroundBehavior</b> into a
   * facility builder.
   */
  abstract class GenericGroundBehavior extends GroundBehavior {

    /**
     * <b>GroundTriggers</b> associated to the behavior.
     * @return
     *   Triggers associated to the behavior.
     */
    def triggers: GroundTriggers
  }

  /**
   * Standard implementation of <b>GenericGroundBehavior</b>.
   */
  case class SimpleGenericGroundBehavior(
    override val triggers: GroundTriggers
  ) extends GenericGroundBehavior

  /**
   * Companion object for <b>GenericGroundBehavior</b>.
   */
  object GenericGroundBehavior {

    /**
     * A <b>GenericBehavior</b> built by its triggers.
     * @param triggers
     *   The <b>GroundTriggers</b> associated to the ground.
     * @return
     *   A <b>GenericBehavior</b> built by its triggers.
     */
    def apply(triggers: GroundTriggers = PartialFunction.empty): GenericGroundBehavior =
      SimpleGenericGroundBehavior(triggers)
  }
}
