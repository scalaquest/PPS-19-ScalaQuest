package io.github.scalaquest.core.model.behaviorBased.commons.itemBehaviors.impl

import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.commons.pushing.CMessagesExt
import io.github.scalaquest.core.model.behaviorBased.commons.reactions.CReactionsExt

/**
 * The trait makes possible to add into the [[BehaviorBasedModel]] the <b>GenericItemBehavior</b>.
 */
trait GenericItemBehaviorExt extends BehaviorBasedModel with CMessagesExt with CReactionsExt {

  /**
   * An ItemBehavior with some triggers. It is useful in order to wrap <b>ItemBehavior</b> into a
   * facility builder.
   */
  abstract class GenericItemBehavior extends ItemBehavior {

    /**
     * <b>ItemTriggers</b> associated to the behavior.
     * @return
     *   Triggers associated to the behavior.
     */
    def triggers: ItemTriggers
  }

  /**
   * Standard implementation of <b>GenericItemBehavior</b>.
   */
  case class SimpleGenericItemBehavior(
    override val triggers: ItemTriggers
  )(implicit val subject: I)
    extends GenericItemBehavior

  /**
   * Companion object for <b>GenericItemBehavior</b>.
   */
  object GenericItemBehavior {

    /**
     * A function that builds a <b>GenericBehavior</b> given a subject.
     * @param triggers
     *   The <b>ItemTriggers</b> associated to the subject.
     * @return
     *   A function that builds a <b>GenericBehavior</b> given a subject.
     */
    def builder(triggers: ItemTriggers = PartialFunction.empty): I => GenericItemBehavior =
      SimpleGenericItemBehavior(triggers)(_)
  }
}
