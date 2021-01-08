package io.github.scalaquest.core.model.default

import io.github.scalaquest.core.model.common.{DefaultCommonBehaviors, DefaultCommonItems}

/**
 * This represents the Model with a standard implementation: items share an internal implementation based on behaviors,
 * with some commonly used items and behaviors. The state uses a simple implementation, that keeps track of the rooms'
 * composition, and a bag for the player.
 */
object DefaultModel extends BehaviorableModel with DefaultCommonBehaviors with DefaultCommonItems with DefaultState
