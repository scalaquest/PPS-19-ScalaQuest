package io.github.scalaquest.core.model.behaviorBased.impl

import io.github.scalaquest.core.model.{Direction, ItemRef, Model, RoomRef}

trait SimpleRoom extends Model {

  override type RM = SimpleRoom

  case class SimpleRoom(
    name: String,
    _items: () => Set[ItemRef],
    _neighbors: () => Map[Direction, RoomRef],
    ref: RoomRef
  ) extends Room {
    override def neighbor(direction: Direction): Option[RoomRef] = _neighbors() get direction
    override def items: Set[ItemRef]                             = _items()
  }

  def roomBuilder(
    name: String,
    neighbors: => Map[Direction, RoomRef],
    items: => Set[ItemRef]
  ): SimpleRoom = SimpleRoom(name, () => items, () => neighbors, RoomRef())

}
