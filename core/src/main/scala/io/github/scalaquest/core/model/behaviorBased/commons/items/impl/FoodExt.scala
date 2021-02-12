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
    addBehaviorsBuilders: I => ItemBehavior*
  ) extends Food {
    final override val eatable: Eatable       = eatableBuilder(this)
    override def behaviors: Seq[ItemBehavior] = eatable +: addBehaviorsBuilders.map(_(this))
  }

  /**
   * Companion object for [[Food]]. Makes the initialization more succinct.
   */
  object Food {

    def apply(
      description: ItemDescription,
      eatableBuilder: I => Eatable,
      addBehaviorsBuilder: Seq[I => ItemBehavior] = Seq.empty
    ): Food = {
      SimpleFood(description, ItemRef(description), eatableBuilder, addBehaviorsBuilder: _*)
    }
  }
}
