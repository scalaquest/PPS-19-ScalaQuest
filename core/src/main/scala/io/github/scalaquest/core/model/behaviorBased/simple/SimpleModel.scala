/*
 * Copyright (c) 2021 ScalaQuest Team.
 *
 * This file is part of ScalaQuest, and is distributed under the terms of the MIT License as
 * described in the file LICENSE in the ScalaQuest distribution's top directory.
 */

package io.github.scalaquest.core.model.behaviorBased.simple

import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.commons.CommonsExt
import io.github.scalaquest.core.model.behaviorBased.simple.impl.{SimpleRoomExt, SimpleStateExt}
import monocle.Lens

/**
 * This represents the <b>Model</b> with a standard implementation: <b>items</b> share an internal
 * implementation based on <b>behaviors</b>, with some commonly used items and behaviors. The state
 * uses a simple implementation, that keeps track of the <b>rooms</b>' composition, and a <b>bag</b>
 * for the player.
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
