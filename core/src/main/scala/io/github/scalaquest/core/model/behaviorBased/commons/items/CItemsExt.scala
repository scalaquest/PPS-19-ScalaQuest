/*
 * Copyright (c) 2021 ScalaQuest Team.
 *
 * This file is part of ScalaQuest, and is distributed under the terms of the MIT License as
 * described in the file LICENSE in the ScalaQuest distribution's top directory.
 */

package io.github.scalaquest.core.model.behaviorBased.commons.items

import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.commons.items.impl.{
  ChestExt,
  DoorExt,
  FoodExt,
  GenericItemExt,
  KeyExt
}

/**
 * When mixed into a Model, it enables the implementation for the common items provided by
 * ScalaQuest Core.
 */
trait CItemsExt
  extends BehaviorBasedModel
  with DoorExt
  with GenericItemExt
  with KeyExt
  with FoodExt
  with ChestExt
