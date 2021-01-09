package io.github.scalaquest.core.model.common.items.std

import io.github.scalaquest.core.model.common.items.StdCommonItemsBase

trait GenericItem extends StdCommonItemsBase {
  case class GenericItem(name: String, override val behaviors: Set[Behavior] = Set()) extends CommonItems.GenericItem
}
