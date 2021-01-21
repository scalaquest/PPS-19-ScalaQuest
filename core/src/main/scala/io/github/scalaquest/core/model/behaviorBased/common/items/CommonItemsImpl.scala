package io.github.scalaquest.core.model.behaviorBased.common.items

import io.github.scalaquest.core.model.Model
import io.github.scalaquest.core.model.behaviorBased.common.CommonBase
import io.github.scalaquest.core.model.behaviorBased.common.items.impl.{
  Door,
  Food,
  GenericItem,
  Key
}

/**
 * When mixed into a [[Model]], it enables the implementation for the common items provided by
 * ScalaQuest Core.
 */
trait CommonItemsImpl extends CommonBase with Door with GenericItem with Key with Food
