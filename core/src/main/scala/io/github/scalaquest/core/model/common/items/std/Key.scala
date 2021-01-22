package io.github.scalaquest.core.model.common.items.std

import io.github.scalaquest.core.model.ItemRef
import io.github.scalaquest.core.model.common.CommonBase
import io.github.scalaquest.core.model.common.items.{CommonItems, StdCommonItems}
import io.github.scalaquest.core.pipeline.parser.ItemDescription

/**
 * The trait makes possible to mix into the [[StdCommonItems]] the standard implementation of
 * [[CommonItems.CommonItems.Key]].
 */
trait Key extends CommonBase {

  /**
   * Standard implementation of the common Key item.
   */
  case class Key(description: ItemDescription, itemRef: ItemRef, additionalBehaviors: Behavior*)
    extends CommonItems.Key {
    override val behaviors: Seq[Behavior] = additionalBehaviors
  }
}
