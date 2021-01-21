package io.github.scalaquest.core.model.behaviorBased.common.items.impl

import io.github.scalaquest.core.model.ItemRef
import io.github.scalaquest.core.model.behaviorBased.common.CommonBase
import io.github.scalaquest.core.model.behaviorBased.common.items.{CommonItems, CommonItemsImpl}

/**
 * The trait makes possible to mix into the [[CommonItemsImpl]] the standard implementation of
 * [[CommonItems.CommonItems.Food]].
 */
trait Food extends CommonBase {

  /**
   * Standard implementation of the standard item Food.
   */
  case class Food(
    itemRef: ItemRef,
    foodBehavior: CommonBehaviors.Eatable,
    additionalBehaviors: Behavior*
  ) extends CommonItems.Food {
    override def behaviors: Seq[Behavior] = foodBehavior +: additionalBehaviors
  }
}
