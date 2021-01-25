package io.github.scalaquest.core.model.behaviorBased.common.items.impl

import io.github.scalaquest.core.model.{ItemDescription, ItemRef}
import io.github.scalaquest.core.model.behaviorBased.common.CommonBase
import io.github.scalaquest.core.model.behaviorBased.common.items.{CommonItems, SimpleCommonItems}

/**
 * The trait makes possible to mix into the [[SimpleCommonItems]] the standard implementation of
 * [[CommonItems.Key]].
 */
trait SimpleKeyExt extends CommonBase {

  /**
   * Standard implementation of the common Key item.
   */
  final case class SimpleKey(
    description: ItemDescription,
    itemRef: ItemRef,
    additionalBehaviors: ItemBehavior*
  ) extends Key {
    override val behaviors: Seq[ItemBehavior] = additionalBehaviors
  }
}
