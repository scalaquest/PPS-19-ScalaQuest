package io.github.scalaquest.core.model.behaviorBased.commons.items.impl

import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.{ItemDescription, ItemRef}
import io.github.scalaquest.core.model.behaviorBased.commons.itemBehaviors.impl.EatableExt

/**
 * The trait makes possible to mix into a [[BehaviorBasedModel]] the Food Item.
 */
trait FoodExt extends BehaviorBasedModel with EatableExt {

  /**
   * A [[BehaviorBasedItem]] that can be eaten by the player.
   */
  trait Food extends BehaviorBasedItem

  /**
   * Standard implementation of the [[Food]] Item.
   */
  case class SimpleFood(
    description: ItemDescription,
    ref: ItemRef,
    foodBehavior: Eatable,
    additionalBehaviors: ItemBehavior*
  ) extends Food {
    override def behaviors: Seq[ItemBehavior] = foodBehavior +: additionalBehaviors
  }

  /**
   * Companion object for [[Food]]. Makes the initialization more succinct.
   */
  object Food {

    def apply(
      description: ItemDescription,
      foodBehavior: Eatable,
      additionalBehaviors: ItemBehavior*
    ): Food = SimpleFood(description, ItemRef(description), foodBehavior, additionalBehaviors: _*)
  }
}
