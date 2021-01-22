package io.github.scalaquest.core.model.common.items.std

import io.github.scalaquest.core.model.ItemRef
import io.github.scalaquest.core.model.common.CommonBase
import io.github.scalaquest.core.model.common.items.{CommonItems, StdCommonItems}
import io.github.scalaquest.core.pipeline.parser.ItemDescription

/**
 * The trait makes possible to mix into the [[StdCommonItems]] the standard implementation of
 * [[CommonItems.CommonItems.Food]].
 */
trait Food extends CommonBase {

  /**
   * Standard implementation of the standard item Food.
   */
  case class Food(
    description: ItemDescription,
    itemRef: ItemRef,
    foodBehavior: CommonBehaviors.Eatable,
    additionalBehaviors: Behavior*
  ) extends CommonItems.Food {
    override def behaviors: Seq[Behavior] = foodBehavior +: additionalBehaviors
  }
}
