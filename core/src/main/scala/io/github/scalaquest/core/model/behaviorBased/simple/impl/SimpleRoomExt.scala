package io.github.scalaquest.core.model.behaviorBased.simple.impl

import io.github.scalaquest.core.model.{Direction, ItemRef, Model, RoomRef}
import monocle.Lens

/**
 * Extension for the model. Adds a base implementation of the [[Model.Room]].
 */
trait SimpleRoomExt extends Model {

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

    override def neighbor(direction: Direction)(implicit state: S): Option[RM] =
      for {
        refInDirection <- _neighbors().get(direction)
        room           <- state.rooms.get(refInDirection)
      } yield room

    override def items(implicit state: S): Set[I] = _items().flatMap(state.items.get)

    override def neighbors(implicit state: S): Map[Direction, RM] =
      _neighbors().map(x => x._1 -> state.rooms(x._2))
  }

  override def roomItemsLens: Lens[RM, Set[ItemRef]] =
    Lens[RM, Set[ItemRef]](get = _._items())(set =
      newItemsSet => oldRoom => oldRoom.copy(_items = () => newItemsSet)
    )

  override def roomDirectionsLens: Lens[RM, Map[Direction, RoomRef]] =
    Lens[RM, Map[Direction, RoomRef]](get = _._neighbors())(set =
      newNeighborsSet => oldRoom => oldRoom.copy(_neighbors = () => newNeighborsSet)
    )
}
