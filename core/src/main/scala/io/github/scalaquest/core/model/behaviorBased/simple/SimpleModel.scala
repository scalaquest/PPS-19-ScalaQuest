package io.github.scalaquest.core.model.behaviorBased.simple

import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.commons.CommonsExt
import io.github.scalaquest.core.model.behaviorBased.simple.builders.BuildersExt
import io.github.scalaquest.core.model.behaviorBased.simple.impl.{SimpleRoomExt, SimpleStateExt}

/**
 * This represents the Model with a standard implementation: items share an internal implementation
 * based on behaviors, with some commonly used items and behaviors. The state uses a simple
 * implementation, that keeps track of the rooms' composition, and a bag for the player.
 */
object SimpleModel
  extends BehaviorBasedModel
  with SimpleStateExt
  with SimpleRoomExt
  with CommonsExt
  with BuildersExt
