package io.github.scalaquest.core.model.behaviorBased.commons.itemBehaviors.impl

import io.github.scalaquest.core.model.Action.Common.Eat
import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.commons.pushing.CommonMessagesExt
import io.github.scalaquest.core.model.behaviorBased.simple.impl.StateUtilsExt

/**
 * The trait makes possible to mix into [[BehaviorBasedModel]] the Eatable behavior.
 */
trait EatableExt extends BehaviorBasedModel with StateUtilsExt with CommonMessagesExt {

  /**
   * A [[ItemBehavior]] associated to an [[Item]] that can be eaten. After an item is eaten, it
   * should be removed from the player bag (or from the current room, if it was there).
   */
  abstract class Eatable extends ItemBehavior

  /**
   * Standard implementation of the Eatable behavior.
   *
   * This is a behavior associated to an Item that can be eaten, if present in the bag or in the
   * room.
   * @param onEatExtra
   *   Reaction to be executed when the item has been successfully eaten, after the standard
   *   [[Reaction]]. It can be omitted.
   */
  case class SimpleEatable(onEatExtra: Option[Reaction] = None) extends Eatable {

    override def triggers: ItemTriggers = {
      // "Eat the item"
      case (Eat, item, None, state) if state.isInScope(item) => eat(item)
    }

    def eat(item: I): Reaction =
      state => {
        // todo locationItemsLens??
        val updLocation = roomItemsLens.modify(_ - item.ref)(state.location)

        state.applyReactions(
          roomsLens.modify(_ + (updLocation.ref -> updLocation)),
          bagLens.modify(_ - item.ref),
          messageLens.modify(_ :+ Eaten(item)),
          onEatExtra.getOrElse(state => state)
        )
      }
  }

  /**
   * Companion object for [[Eatable]]. Shortcut for the standard implementation.
   */
  object Eatable {
    def apply(onEatExtra: Option[Reaction] = None): Eatable = SimpleEatable(onEatExtra)
  }
}
