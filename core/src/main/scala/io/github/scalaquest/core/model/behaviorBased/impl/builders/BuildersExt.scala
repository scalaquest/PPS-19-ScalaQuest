package io.github.scalaquest.core.model.behaviorBased.impl.builders

import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.impl.builders.impl.{
  DoorKeyBuilderExt,
  RoomBuilderExt,
  StateBuilderExt
}

/**
 * Utilities to easily build various parts, for the storyteller. todo can be articulated even more
 */
trait BuildersExt
  extends BehaviorBasedModel
  with DoorKeyBuilderExt
  with RoomBuilderExt
  with StateBuilderExt
