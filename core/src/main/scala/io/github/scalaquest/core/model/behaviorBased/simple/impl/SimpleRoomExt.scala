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
   *   The [[Room]] name.
   * @param _items
   *   the [[ItemRef]] that refers to the <b>Item</b> contained by this <b>SimpleRoom</b>.
   * @param _neighbors
   *   [[Map]] with neighbors of this <b>SimpleRoom</b>.
   * @param ref
   *   The [[RoomRef]] for this <b>SimpleRoom</b>.
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

  /**
   * Companion object with apply for <b>SimpleRoom</b>.
   */
  object Room {

    /**
     * A base implementation for <b>Room</b>.
     * @param name
     *   The <b>Room</b> name.
     * @param neighbors
     *   Map that define all <b>neighbors</b> of this room.
     * @param items
     *   All the <b>items</b> in this match.
     */
    def apply(
      name: String,
      neighbors: => Map[Direction, RoomRef] = Map.empty,
      items: => Set[ItemRef] = Set.empty
    ): RM = SimpleRoom(name, () => items, () => neighbors, RoomRef(name))
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
