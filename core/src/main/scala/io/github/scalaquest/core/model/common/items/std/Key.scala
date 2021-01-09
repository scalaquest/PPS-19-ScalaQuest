package io.github.scalaquest.core.model.common.items.std

import io.github.scalaquest.core.model.common.items.StdCommonItemsBase

trait Key extends StdCommonItemsBase {

  case class Key(name: String, additionalBehaviors: Set[Behavior] = Set()) extends CommonItems.Key {
    override val behaviors: Set[Behavior] = additionalBehaviors
  }
}
