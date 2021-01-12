package io.github.scalaquest.core.model.common.items.std

import io.github.scalaquest.core.model.common.items.{CommonItems, StdCommonItems, StdCommonItemsBase}

/**
 * The trait makes possible to mix into the [[StdCommonItems]] the standard implementation of
 * [[CommonItems.CommonItems.Key]].
 */
private[behaviors] trait Key extends StdCommonItemsBase {

  /**
   * Standard implementation of the common Key item.
   */
  case class Key(name: String, additionalBehaviors: Set[Behavior] = Set()) extends CommonItems.Key {
    override val behaviors: Set[Behavior] = additionalBehaviors
  }
}
