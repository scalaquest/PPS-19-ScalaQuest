package io.github.scalaquest.core.model.behaviorBased.common.items

import io.github.scalaquest.core.model.Model
import io.github.scalaquest.core.model.behaviorBased.common.CommonBase
import io.github.scalaquest.core.model.behaviorBased.common.items.impl.{
  SimpleDoorExt,
  SimpleFoodExt,
  SimpleGenericItemExt,
  SimpleKeyExt
}

/**
 * When mixed into a [[Model]], it enables the implementation for the common items provided by
 * ScalaQuest Core.
 */
trait SimpleCommonItems
  extends CommonBase
  with SimpleDoorExt
  with SimpleGenericItemExt
  with SimpleKeyExt
  with SimpleFoodExt
