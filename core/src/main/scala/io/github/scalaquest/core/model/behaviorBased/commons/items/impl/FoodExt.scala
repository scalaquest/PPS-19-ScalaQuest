package io.github.scalaquest.core.model.behaviorBased.commons.items.impl

import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.commons.itemBehaviors.impl.EatableExt
import io.github.scalaquest.core.model.{ItemDescription, ItemRef}

/**
 * The trait makes possible to mix into a [[BehaviorBasedModel]] the Food Item.
 */
trait FoodExt extends BehaviorBasedModel with EatableExt {

  /**
   * A [[BehaviorBasedItem]] that can be eaten by the player.
   */
  trait Food extends BehaviorBasedItem {
    def eatable: Eatable
  }

  /**
   * Standard implementation of the [[Food]] Item.
   */
  case class SimpleFood(
    description: ItemDescription,
    ref: ItemRef,
    eatableBuilder: I => Eatable,
    extraBehavBuilders: Seq[I => ItemBehavior] = Seq.empty
  ) extends Food {
    final override val eatable: Eatable       = eatableBuilder(this)
    override val behaviors: Seq[ItemBehavior] = eatable +: extraBehavBuilders.map(_(this))
  }

  /**
   * Companion object for [[Food]]. Makes the initialization more succinct.
   */
  object Food {

    def apply(
      description: ItemDescription,
      eatableBuilder: I => Eatable,
      extraBehavBuilders: Seq[I => ItemBehavior] = Seq.empty
    ): Food = {
      SimpleFood(description, ItemRef(description), eatableBuilder, extraBehavBuilders)
    }
  }
}
