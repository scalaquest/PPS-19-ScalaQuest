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

    /**
     * @return
     *   the [[Eatable]] behavior.
     */
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

    /**
     * Facilitates the creation of a [[SimpleFood]].
     * @param description
     *   the food's [[ItemDescription]].
     * @param eatableBuilder
     *   the eatable behavior builder.
     * @param extraBehavBuilders
     *   some possible extra behavior for the items.
     * @return
     *   an instance of SimpleDoor.
     */
    def apply(
      description: ItemDescription,
      eatableBuilder: I => Eatable,
      extraBehavBuilders: Seq[I => ItemBehavior] = Seq.empty
    ): Food = {
      SimpleFood(description, ItemRef(description), eatableBuilder, extraBehavBuilders)
    }
  }
}
