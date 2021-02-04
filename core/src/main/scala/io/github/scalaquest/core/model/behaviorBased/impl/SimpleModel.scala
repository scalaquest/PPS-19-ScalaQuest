package io.github.scalaquest.core.model.behaviorBased.impl

import io.github.scalaquest.core.model.{Direction, ItemRef, Message, RoomRef}
import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.common.itemBehaviors.SimpleCommonBehaviors
import io.github.scalaquest.core.model.behaviorBased.common.items.SimpleCommonItems
import io.github.scalaquest.core.model.impl.{SimpleRoom, SimpleState}
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
  with SimpleGround
  with SimpleRoom {

  override implicit def playerBagLens: Lens[S, Set[ItemRef]]      = GenLens[S](_.matchState.player.bag)
  override implicit def matchRoomsLens: Lens[S, Map[RoomRef, RM]] = GenLens[S](_.matchState.rooms)
  override implicit def itemsLens: Lens[S, Map[ItemRef, I]]       = GenLens[S](_.matchState.items)
  override implicit def messageLens: Lens[S, Seq[Message]]        = GenLens[S](_.messages)

  override implicit def roomDirectionsLens: Lens[RM, Map[Direction, RoomRef]] =
    Lens[RM, Map[Direction, RoomRef]](get = _._neighbors())(set =
      a => b => b.copy(_neighbors = () => a)
    )

  override implicit def playerLocationLens: Lens[S, RoomRef] =
    GenLens[S](_.matchState.player.location)

  override implicit def roomLens: Lens[RM, Set[ItemRef]] =
    Lens[RM, Set[ItemRef]](get = _.items)(set = a => b => b.copy(_items = () => a))
}
