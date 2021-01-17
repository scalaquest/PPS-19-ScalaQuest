package io.github.scalaquest.core.model.common.items.std

import io.github.scalaquest.core.model.common.items.{
  CommonItems,
  StdCommonItems,
  StdCommonItemsBase
}

/**
 * The trait makes possible to mix into the [[StdCommonItems]] the standard implementation of
 * [[CommonItems.CommonItems.Key]].
 */
trait Key extends StdCommonItemsBase {

  /**
   * Standard implementation of the common Key item.
   */
  case class Key(name: String, additionalBehaviors: Behavior*) extends CommonItems.Key {
    override val behaviors: Seq[Behavior] = additionalBehaviors
  }
}
