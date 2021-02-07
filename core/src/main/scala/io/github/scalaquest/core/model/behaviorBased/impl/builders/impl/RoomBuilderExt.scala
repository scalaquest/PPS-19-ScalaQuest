package io.github.scalaquest.core.model.behaviorBased.impl.builders.impl

import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.impl.SimpleRoomExt
import io.github.scalaquest.core.model.{Direction, ItemRef, RoomRef}

trait RoomBuilderExt extends BehaviorBasedModel with SimpleRoomExt {

  def roomBuilder(
    name: String,
    neighbors: => Map[Direction, RoomRef],
    items: => Set[ItemRef]
  ): SimpleRoom = SimpleRoom(name, () => items, () => neighbors, RoomRef(name))
}
