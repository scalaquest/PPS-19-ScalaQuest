package io.github.scalaquest.core.model.behaviorBased.commons.itemBehaviors.impl

import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.commons.pushing.CommonMessagesExt
import io.github.scalaquest.core.model.behaviorBased.commons.reactions.CommonReactionsExt

/**
 * The trait makes possible to mix into the [[BehaviorBasedModel]] the Takeable behavior.
 */
trait GenericBehaviorExt extends BehaviorBasedModel with CommonMessagesExt with CommonReactionsExt {

  /**
   * A [[ItemBehavior]] associated to an [[Item]] that can be taken and put away into the bag of the
   * player.
   */
  abstract class GenericBehavior extends ItemBehavior {

    /**
     * @return
     *   generic triggers associated to this ItemBehavior.
     */
    def triggers: ItemTriggers
  }

  /**
   * Standard implementation of
   *
   * The behavior of an Item that could be put into the Bag of the player from the current room.
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
     * Builder shortcut for the standard implementation of [[SimpleGenericBehavior]].
     * @param triggers
     *   the [[ItemTriggers]] associated to the item.
     * @return
     *   an instance of [[SimpleGenericBehavior]].
     */
    def builder(triggers: ItemTriggers = PartialFunction.empty): I => GenericBehavior =
      SimpleGenericBehavior(triggers)(_)
  }
}
