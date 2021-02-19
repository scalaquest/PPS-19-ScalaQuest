package io.github.scalaquest.core.model.behaviorBased.simple

import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.commons.CommonsExt
import io.github.scalaquest.core.model.behaviorBased.simple.impl.{SimpleRoomExt, SimpleStateExt}
import monocle.Lens

/**
 * This represents the Model with a standard implementation: items share an internal implementation
 * based on behaviors, with some commonly used items and behaviors. The state uses a simple
 * implementation, that keeps track of the rooms' composition, and a bag for the player.
 */
object SimpleModel
  extends BehaviorBasedModel
  with SimpleStateExt
  with SimpleRoomExt
  with CommonsExt {

  override def locationRoomLens: Lens[SimpleModel.SimpleState, SimpleModel.SimpleRoom] =
    Lens[S, RM] { s => s.location } { r => s =>
      s.copy(rooms = s.rooms.updated(s.location.ref, r), _location = r.ref)
    }
}
