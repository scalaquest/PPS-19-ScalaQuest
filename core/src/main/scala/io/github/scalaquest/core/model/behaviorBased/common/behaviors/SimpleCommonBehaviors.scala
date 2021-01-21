package io.github.scalaquest.core.model.behaviorBased.common.behaviors

import io.github.scalaquest.core.model.Model
import io.github.scalaquest.core.model.behaviorBased.common.CommonBase
import io.github.scalaquest.core.model.behaviorBased.common.behaviors.impl.{
  SimpleEatable,
  SimpleOpenable,
  SimpleRoomLink,
  SimpleTakeable
}

/**
 * When mixed into a [[Model]], it enables the implementation for the common behaviors provided by
 * ScalaQuest Core. It requires the storyteller to implement all the required [[monocle.Lens]], used
 * by the implementation to access and re-generate the concrete [[Model.State]].
 */
trait SimpleCommonBehaviors
  extends CommonBase
  with SimpleTakeable
  with SimpleEatable
  with SimpleOpenable
  with SimpleRoomLink
