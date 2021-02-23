package io.github.scalaquest.core.model.behaviorBased.commons.itemBehaviors.impl

import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.commons.pushing.CommonMessagesExt
import io.github.scalaquest.core.model.behaviorBased.commons.reactions.CommonReactionsExt

/**
 * The trait makes possible to add into the [[BehaviorBasedModel]] the <b>GenericBehavior</b>.
 */
trait GenericBehaviorExt extends BehaviorBasedModel with CommonMessagesExt with CommonReactionsExt {

  /**
   * An ItemBehavior with some triggers. It is useful in order to wrap <b>ItemBehavior</b> into a
   * facility builder.
   */
  abstract class GenericBehavior extends ItemBehavior {

    /**
     * <b>ItemTriggers</b> associated to the behavior.
     * @return
     *   Triggers associated to the behavior.
     */
    def triggers: ItemTriggers
  }

  /**
   * Standard implementation of <b>GenericBehavior</b>.
   */
  case class SimpleGenericBehavior(
    override val triggers: ItemTriggers
  )(implicit val subject: I)
    extends GenericBehavior

  /**
   * Companion object for <b>GenericBehavior</b>.
   */
  object GenericBehavior {

    /**
     * A function that builds a <b>GenericBehavior</b> given a subject.
     * @param triggers
     *   The <b>ItemTriggers</b> associated to the subject.
     * @return
     *   A function that builds a <b>GenericBehavior</b> given a subject.
     */
    def builder(triggers: ItemTriggers = PartialFunction.empty): I => GenericBehavior =
      SimpleGenericBehavior(triggers)(_)
  }
}
