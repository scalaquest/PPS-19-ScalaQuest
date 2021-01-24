package io.github.scalaquest.core.model.behaviorBased.common.items.impl

import io.github.scalaquest.core.model.ItemRef
import io.github.scalaquest.core.model.behaviorBased.common.CommonBase
import io.github.scalaquest.core.model.behaviorBased.common.items.{CommonItems, SimpleCommonItems}

/**
 * The trait makes possible to mix into the [[SimpleCommonItems]] the standard implementation of
 * [[CommonItems.Food]].
 */
trait SimpleFoodExt extends CommonBase {

  /**
   * Standard implementation of the standard item Food.
   */
  case class SimpleFood(
    itemRef: ItemRef,
    foodBehavior: Eatable,
    additionalBehaviors: ItemBehavior*
  ) extends Food {
    override def behaviors: Seq[ItemBehavior] = foodBehavior +: additionalBehaviors
  }
}
