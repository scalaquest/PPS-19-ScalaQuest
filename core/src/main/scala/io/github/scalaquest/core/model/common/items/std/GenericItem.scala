package io.github.scalaquest.core.model.common.items.std

import io.github.scalaquest.core.model.common.items.{CommonItems, StdCommonItems, StdCommonItemsBase}

/**
 * The trait makes possible to mix into the [[StdCommonItems]] the standard implementation of
 * [[CommonItems.CommonItems.GenericItem]].
 */
trait GenericItem extends StdCommonItemsBase {

  /**
   * Standard implementation of the common GenericItem.
   */
  case class GenericItem(name: String, override val behaviors: Set[Behavior] = Set()) extends CommonItems.GenericItem
}
