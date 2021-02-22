package io.github.scalaquest.core.model.behaviorBased.commons.itemBehaviors.impl

import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.commons.actioning.CommonActions.Eat
import io.github.scalaquest.core.model.behaviorBased.commons.pushing.CommonMessagesExt
import io.github.scalaquest.core.model.behaviorBased.commons.reactions.CommonReactionsExt

/**
 * The trait makes possible to mix into [[BehaviorBasedModel]] the Eatable behavior.
 */
trait EatableExt extends BehaviorBasedModel with CommonMessagesExt with CommonReactionsExt {

  /**
   * A [[ItemBehavior]] associated to an [[Item]] that can be eaten. After an item is eaten, it
   * should be removed from the player bag (or from the current room, if it was there).
   */
  abstract class Eatable extends ItemBehavior {

    /**
     * @return
     *   the eat [[Reaction]]
     */
    def eat: Reaction
  }

  /**
   * Standard implementation of the Eatable behavior.
   *
   * This is a behavior associated to an Item that can be eaten, if present in the bag or in the
   * room.
   * @param onEatExtra
   *   Reaction to be executed when the item has been successfully eaten, after the standard
   *   [[Reaction]]. It can be omitted.
   */
  case class SimpleEatable(onEatExtra: Reaction = Reaction.empty)(implicit subject: I)
    extends Eatable {

    override def triggers: ItemTriggers = {
      case (Eat, item, None, state) if state.isInScope(item) => eat
    }

    override def eat: Reaction =
      Reaction.combine(
        Reactions.eat(subject),
        onEatExtra
      )
  }

  /**
   * Companion object for [[Eatable]].
   */
  object Eatable {

    /**
     * Facilitates for the simple implementation of Eatable.
     * @param onEatExtra
     *   a possible extra behavior.
     * @return
     *   a builder that given an Item build a SimpleEatable instance.
     */
    def builder(onEatExtra: Reaction = Reaction.empty): I => Eatable = SimpleEatable(onEatExtra)(_)
  }
}
