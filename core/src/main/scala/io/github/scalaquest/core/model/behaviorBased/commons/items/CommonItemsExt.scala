package io.github.scalaquest.core.model.behaviorBased.commons.items

import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.commons.items.impl.{
  DoorExt,
  FoodExt,
  GenericItemExt,
  KeyExt
}

/**
 * When mixed into a Model, it enables the implementation for the common items provided by
 * ScalaQuest Core.
 */
trait CommonItemsExt
  extends BehaviorBasedModel
  with DoorExt
  with GenericItemExt
  with KeyExt
  with FoodExt
