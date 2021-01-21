package io.github.scalaquest.core.model.behaviorBased.common.items.impl

import io.github.scalaquest.core.model.ItemRef
import io.github.scalaquest.core.model.behaviorBased.common.CommonBase
import io.github.scalaquest.core.model.behaviorBased.common.items.{CommonItems, CommonItemsImpl}

/**
 * The trait makes possible to mix into the [[CommonItemsImpl]] the standard implementation of
 * [[CommonItems.CommonItems.Key]].
 */
trait Key extends CommonBase {

  /**
   * Standard implementation of the common Key item.
   */
  case class Key(itemRef: ItemRef, additionalBehaviors: Behavior*) extends CommonItems.Key {
    override val behaviors: Seq[Behavior] = additionalBehaviors
  }
}
