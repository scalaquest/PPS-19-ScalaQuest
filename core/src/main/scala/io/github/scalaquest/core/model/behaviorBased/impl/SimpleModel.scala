package io.github.scalaquest.core.model.behaviorBased.impl

import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.common.itemBehaviors.SimpleCommonBehaviors
import io.github.scalaquest.core.model.behaviorBased.common.items.SimpleCommonItems

/**
 * This represents the Model with a standard implementation: items share an internal implementation
 * based on behaviors, with some commonly used items and behaviors. The state uses a simple
 * implementation, that keeps track of the rooms' composition, and a bag for the player.
 *
 * As common behaviors needs some Lens to be passed in, to regenerate the State, this Model also
 * implements the required lens.
 */
object SimpleModel
  extends BehaviorBasedModel
  with SimpleCommonBehaviors
  with SimpleCommonItems
  with SimpleState
  with SimpleGround
  with SimpleRoom
  with SimpleBuilders
