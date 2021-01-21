package io.github.scalaquest.core.model.behaviorBased.common.items.impl

import io.github.scalaquest.core.model.ItemRef
import io.github.scalaquest.core.model.behaviorBased.common.CommonBase
import io.github.scalaquest.core.model.behaviorBased.common.items.{CommonItems, CommonItemsImpl}

/**
 * The trait makes possible to mix into the [[CommonItemsImpl]] the standard implementation of
 * [[CommonItems.CommonItems.GenericItem]].
 */
trait GenericItem extends CommonBase {

  /**
   * Standard implementation of the common GenericItem.
   */
  case class GenericItem(itemRef: ItemRef, additionalBehaviors: Behavior*)
    extends CommonItems.GenericItem {
    override def behaviors: Seq[Behavior] = additionalBehaviors
  }
}
