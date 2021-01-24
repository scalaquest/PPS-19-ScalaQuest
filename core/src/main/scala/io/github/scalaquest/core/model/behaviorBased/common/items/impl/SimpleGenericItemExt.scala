package io.github.scalaquest.core.model.behaviorBased.common.items.impl

import io.github.scalaquest.core.model.ItemRef
import io.github.scalaquest.core.model.behaviorBased.common.CommonBase
import io.github.scalaquest.core.model.behaviorBased.common.items.{CommonItems, SimpleCommonItems}
import io.github.scalaquest.core.pipeline.parser.ItemDescription

/**
 * The trait makes possible to mix into the [[SimpleCommonItems]] the standard implementation of
 * [[CommonItems.GenericItem]].
 */
trait SimpleGenericItemExt extends CommonBase {

  /**
   * Standard implementation of the common GenericItem.
   */
  final case class SimpleGenericItem(
    description: ItemDescription,
    itemRef: ItemRef,
    additionalBehaviors: ItemBehavior*
  ) extends GenericItem {
    override def behaviors: Seq[ItemBehavior] = additionalBehaviors
  }
}
