package io.github.scalaquest.core.model.behaviorBased.commons.itemBehaviors.impl

import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.commons.pushing.CommonMessagesExt
import io.github.scalaquest.core.model.behaviorBased.commons.reactions.CommonReactionsExt

/**
 * The trait makes possible to mix into the [[BehaviorBasedModel]] the Takeable behavior.
 */
trait GenericBehaviorExt extends BehaviorBasedModel with CommonMessagesExt with CommonReactionsExt {

  /**
   * An ItemBehavior with some triggers. It is useful in order to wrap [[ItemBehavior]] into a
   * facility builder.
   */
  abstract class GenericBehavior extends ItemBehavior {

    /**
     * Triggers associated to the behavior.
     * @return
     *   Triggers associated to the behavior.
     */
    def triggers: ItemTriggers
  }

  /**
   * Standard implementation of GenericBehavior.
   */
  case class SimpleGenericBehavior(
    override val triggers: ItemTriggers
  )(implicit val subject: I)
    extends GenericBehavior

  /**
   * Companion object for Generic Behavior.
   */
  object GenericBehavior {

    /**
     * A function that builds a GenericBehavior given a subject Item.
     * @param triggers
     *   The [[ItemTriggers]] associated to the item.
     * @return
     *   A function that builds a GenericBehavior given a subject Item.
     */
    def builder(triggers: ItemTriggers = PartialFunction.empty): I => GenericBehavior =
      SimpleGenericBehavior(triggers)(_)
  }
}
