package io.github.scalaquest.core.model.common.items.std

import io.github.scalaquest.core.model.common.items.{
  CommonItems,
  StdCommonItems,
  StdCommonItemsBase
}
import io.github.scalaquest.core.model.std.StdModel.Eatable

/**
 * The trait makes possible to mix into the [[StdCommonItems]] the standard implementation of
 * [[CommonItems.CommonItems.Food]].
 */
trait Food extends StdCommonItemsBase {

  /**
   * Standard implementation of the standard item Food.
   */
  case class Food(
    name: String,
    foodBehavior: CommonBehaviors.Eatable,
    additionalBehaviors: Behavior*
  ) extends CommonItems.Food {
    override def behaviors: Seq[Behavior] = foodBehavior +: additionalBehaviors
  }
}
