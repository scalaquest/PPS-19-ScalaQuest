package io.github.scalaquest.core.model.behaviorBased.common.items

import io.github.scalaquest.core.model.Model
import io.github.scalaquest.core.model.behaviorBased.common.CommonBase
import io.github.scalaquest.core.model.behaviorBased.common.items.impl.{
  SimpleDoor,
  SimpleFood,
  SimpleGenericItem,
  SimpleKey
}

/**
 * When mixed into a [[Model]], it enables the implementation for the common items provided by
 * ScalaQuest Core.
 */
trait SimpleCommonItems
  extends CommonBase
  with SimpleDoor
  with SimpleGenericItem
  with SimpleKey
  with SimpleFood
