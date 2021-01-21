package io.github.scalaquest.core.model.behaviorBased.common.items.impl

import io.github.scalaquest.core.model.ItemRef
import io.github.scalaquest.core.model.behaviorBased.common.CommonBase
import io.github.scalaquest.core.model.behaviorBased.common.items.{CommonItems, SimpleCommonItems}

/**
 * The trait makes possible to mix into the [[SimpleCommonItems]] the standard implementation of
 * [[CommonItems.GenericItem]].
 */
trait SimpleGenericItem extends CommonBase {

  /**
   * Standard implementation of the common GenericItem.
   */
  case class SimpleGenericItem(itemRef: ItemRef, additionalBehaviors: Behavior*)
    extends GenericItem {
    override def behaviors: Seq[Behavior] = additionalBehaviors
  }
}
