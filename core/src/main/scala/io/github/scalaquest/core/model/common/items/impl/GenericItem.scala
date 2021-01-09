package io.github.scalaquest.core.model.common.items.impl

import io.github.scalaquest.core.model.common.items.DefaultCommonItemsBase

trait GenericItem extends DefaultCommonItemsBase {
  case class GenericItem(name: String, override val behaviors: Set[Behavior] = Set()) extends CommonItems.GenericItem
}
