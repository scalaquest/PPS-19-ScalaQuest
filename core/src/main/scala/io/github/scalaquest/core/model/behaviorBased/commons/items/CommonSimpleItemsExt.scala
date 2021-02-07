package io.github.scalaquest.core.model.behaviorBased.commons.items

import io.github.scalaquest.core.model.Model
import io.github.scalaquest.core.model.behaviorBased.commons.CommonBase
import io.github.scalaquest.core.model.behaviorBased.commons.items.impl.{
  SimpleDoorExt,
  SimpleFoodExt,
  SimpleGenericItemExt,
  SimpleKeyExt
}

/**
 * When mixed into a [[Model]], it enables the implementation for the common items provided by
 * ScalaQuest Core.
 */
trait CommonSimpleItemsExt
  extends CommonBase
  with SimpleDoorExt
  with SimpleGenericItemExt
  with SimpleKeyExt
  with SimpleFoodExt
