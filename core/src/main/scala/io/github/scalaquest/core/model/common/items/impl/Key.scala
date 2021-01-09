package io.github.scalaquest.core.model.common.items.impl

import io.github.scalaquest.core.model.common.items.DefaultCommonItemsBase

trait Key extends DefaultCommonItemsBase {

  case class Key(name: String, additionalBehaviors: Set[Behavior] = Set()) extends CommonItems.Key {
    override val behaviors: Set[Behavior] = additionalBehaviors
  }
}
