/*
 * Copyright (c) 2021 ScalaQuest Team.
 *
 * This file is part of ScalaQuest, and is distributed under the terms of the MIT License as
 * described in the file LICENSE in the ScalaQuest distribution's top directory.
 */

package io.github.scalaquest.core.model.behaviorBased.commons.itemBehaviors

import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.commons.itemBehaviors.impl.{
  ContainerExt,
  EatableExt,
  GenericItemBehaviorExt,
  OpenableExt,
  RoomLinkExt,
  TakeableExt
}

/**
 * When mixed into a Model, it enables the implementation for the common behaviors provided by
 * ScalaQuest Core. It requires the storyteller to implement all the required [[monocle.Lens]], used
 * by the implementation to access and re-generate the concrete State.
 */
trait CItemBehaviorsExt
  extends BehaviorBasedModel
  with TakeableExt
  with EatableExt
  with OpenableExt
  with RoomLinkExt
  with ContainerExt
  with GenericItemBehaviorExt
