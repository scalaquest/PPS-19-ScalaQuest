package io.github.scalaquest.core.model.behaviorBased.common.items.impl

import io.github.scalaquest.core.model.ItemRef
import io.github.scalaquest.core.model.behaviorBased.common.CommonBase
import io.github.scalaquest.core.model.behaviorBased.common.items.{CommonItems, SimpleCommonItems}
import io.github.scalaquest.core.pipeline.parser.ItemDescription

/**
 * The trait makes possible to mix into the [[SimpleCommonItems]] the standard implementation of
 * [[CommonItems.Food]].
 */
trait SimpleFoodExt extends CommonBase {

  /**
   * Standard implementation of the standard item Food.
   */
  final case class SimpleFood(
    description: ItemDescription,
    itemRef: ItemRef,
    foodBehavior: Eatable,
    additionalBehaviors: ItemBehavior*
  ) extends Food {
    override def behaviors: Seq[ItemBehavior] = foodBehavior +: additionalBehaviors
  }
}
