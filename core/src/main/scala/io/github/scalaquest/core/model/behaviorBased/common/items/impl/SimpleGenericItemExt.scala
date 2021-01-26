package io.github.scalaquest.core.model.behaviorBased.common.items.impl

import io.github.scalaquest.core.model.{ItemDescription, ItemRef}
import io.github.scalaquest.core.model.behaviorBased.common.CommonBase
import io.github.scalaquest.core.model.behaviorBased.common.items.{CommonItems, SimpleCommonItems}

/**
 * The trait makes possible to mix into the [[SimpleCommonItems]] the standard implementation of
 * [[CommonItems.GenericItem]].
 */
trait SimpleGenericItemExt extends CommonBase {

  /**
   * Standard implementation of the common GenericItem.
   */
  case class SimpleGenericItem(
    description: ItemDescription,
    ref: ItemRef,
    additionalBehaviors: ItemBehavior*
  ) extends GenericItem {
    override def behaviors: Seq[ItemBehavior] = additionalBehaviors
  }
}
