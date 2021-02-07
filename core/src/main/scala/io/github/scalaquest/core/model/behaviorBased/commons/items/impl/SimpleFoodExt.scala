package io.github.scalaquest.core.model.behaviorBased.commons.items.impl

import io.github.scalaquest.core.model.{ItemDescription, ItemRef}
import io.github.scalaquest.core.model.behaviorBased.commons.CommonBase
import io.github.scalaquest.core.model.behaviorBased.commons.items.{
  CommonItemsExt,
  CommonSimpleItemsExt
}

/**
 * The trait makes possible to mix into the [[CommonSimpleItemsExt]] the standard implementation of
 * [[CommonItemsExt.Food]].
 */
trait SimpleFoodExt extends CommonBase {

  /**
   * Standard implementation of the standard item Food.
   */
  case class SimpleFood(
    description: ItemDescription,
    ref: ItemRef,
    foodBehavior: Eatable,
    additionalBehaviors: ItemBehavior*
  ) extends Food {
    override def behaviors: Seq[ItemBehavior] = foodBehavior +: additionalBehaviors
  }
}
