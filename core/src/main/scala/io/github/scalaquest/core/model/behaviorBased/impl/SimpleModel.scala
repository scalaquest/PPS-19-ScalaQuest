package io.github.scalaquest.core.model.behaviorBased.impl

import io.github.scalaquest.core.model.Room
import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.common.itemBehaviors.SimpleCommonBehaviors
import io.github.scalaquest.core.model.behaviorBased.common.items.SimpleCommonItems
import monocle.Lens
import monocle.macros.GenLens

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
  with SimpleGround {
  override implicit def playerBagLens: Lens[S, Set[I]] = GenLens[S](_.matchState.player.bag)

  override implicit def geographyLens: Lens[S, Map[Room, Set[I]]] =
    GenLens[S](_.matchState.geography)
  override implicit def playerLocationLens: Lens[S, Room] = GenLens[S](_.matchState.player.location)
}
