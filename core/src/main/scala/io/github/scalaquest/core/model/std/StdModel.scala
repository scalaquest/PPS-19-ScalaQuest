package io.github.scalaquest.core.model.std

import io.github.scalaquest.core.model.Room
import io.github.scalaquest.core.model.common.behaviors.StdCommonBehaviors
import io.github.scalaquest.core.model.common.items.StdCommonItems
import monocle.Lens
import monocle.macros.GenLens

/**
 * This represents the Model with a standard implementation: items share an internal implementation based on behaviors,
 * with some commonly used items and behaviors. The state uses a simple implementation, that keeps track of the rooms'
 * composition, and a bag for the player.
 *
 * As common behaviors needs some Lens to be passed in, to regenerate the State, this Model also implements the required
 * lens.
 */
object StdModel extends BehaviorableModel with StdCommonBehaviors with StdCommonItems with StdState {
  override implicit def bagLens: Lens[S, Set[I]]              = GenLens[S](_.game.player.bag)
  override implicit def itemsLens: Lens[S, Map[Room, Set[I]]] = GenLens[S](_.game.itemsInRooms)
  override implicit def currRoomLens: Lens[S, Room]           = GenLens[S](_.game.player.location)
}
