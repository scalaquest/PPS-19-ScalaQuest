package io.github.scalaquest.core.model.behaviorBased.commons.itemBehaviors.impl

import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.commons.actioning.CommonActions.Eat
import io.github.scalaquest.core.model.behaviorBased.commons.pushing.CommonMessagesExt
import io.github.scalaquest.core.model.behaviorBased.commons.reactions.CommonReactionsExt
import io.github.scalaquest.core.model.behaviorBased.simple.impl.StateUtilsExt

/**
 * The trait makes possible to mix into [[BehaviorBasedModel]] the Eatable behavior.
 */
trait EatableExt
  extends BehaviorBasedModel
  with StateUtilsExt
  with CommonMessagesExt
  with CommonReactionsExt {

  /**
   * A [[ItemBehavior]] associated to an [[Item]] that can be eaten. After an item is eaten, it
   * should be removed from the player bag (or from the current room, if it was there).
   */
  abstract class Eatable extends ItemBehavior {
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
  case class SimpleEatable(onEatExtra: Option[Reaction] = None)(implicit subject: I)
    extends Eatable {

    override def triggers: ItemTriggers = {
      // "Eat the item"
      case (Eat, item, None, state) if state.isInScope(item) => eat
    }

    def eat: Reaction =
      Reaction.foldV(
        Reactions.eat(subject),
        onEatExtra.getOrElse(Reaction.empty)
      )
  }

  /**
   * Companion object for [[Eatable]]. Shortcut for the standard implementation.
   */
  object Eatable {
    def builder(onEatExtra: Option[Reaction] = None): I => Eatable = SimpleEatable(onEatExtra)(_)
    def builder(onEatExtra: Reaction): I => Eatable                = SimpleEatable(Some(onEatExtra))(_)
  }
}
