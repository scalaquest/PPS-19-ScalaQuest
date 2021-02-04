package io.github.scalaquest.core.model.impl

import io.github.scalaquest.core.model.{Direction, ItemRef, Model, RoomRef}

/**
 * Extension for the model. Adds a base implementation of the [[Model.Room]].
 */
trait SimpleRoom extends Model {

  override type RM = SimpleRoom

  /**
   * A base implementation for [[Room]].
   * @param name
   *   the [[Room]] name.
   * @param _items
   *   the [[ItemRef]] that refers to the [[Model.Item]] contained by this [[SimpleRoom]].
   * @param _neighbors
   *   a [[Map]] with neighbors of this [[SimpleRoom]].
   * @param ref
   *   the [[RoomRef]] for this [[SimpleRoom]].
   */
  case class SimpleRoom(
    name: String,
    _items: () => Set[ItemRef],
    _neighbors: () => Map[Direction, RoomRef],
    ref: RoomRef
  ) extends Room {
    override def neighbor(direction: Direction): Option[RoomRef] = _neighbors() get direction
    override def items: Set[ItemRef]                             = _items()
  }

  /**
   * Companion object with a factory to build the [[Room]] with the right constraints of the given
   * extension.
   */
  object Room {

    def apply(
      name: String,
      neighbors: => Map[Direction, RoomRef],
      items: => Set[ItemRef]
    ): SimpleRoom = SimpleRoom(name, () => items, () => neighbors, RoomRef(name))
  }
}
