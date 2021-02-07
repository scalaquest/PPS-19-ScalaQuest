package io.github.scalaquest.core.model.behaviorBased.commons.items.impl

import io.github.scalaquest.core.model.{ItemDescription, ItemRef}
import io.github.scalaquest.core.model.behaviorBased.commons.CommonBase
import io.github.scalaquest.core.model.behaviorBased.commons.items.{
  CommonItemsExt,
  CommonSimpleItemsExt
}

/**
 * The trait makes possible to mix into the [[CommonSimpleItemsExt]] the standard implementation of
 * [[CommonItemsExt.Key]].
 */
trait SimpleKeyExt extends CommonBase {

  /**
   * Standard implementation of the common Key item.
   */
  case class SimpleKey(
    description: ItemDescription,
    ref: ItemRef,
    additionalBehaviors: ItemBehavior*
  ) extends Key {
    override val behaviors: Seq[ItemBehavior] = additionalBehaviors
  }
}
