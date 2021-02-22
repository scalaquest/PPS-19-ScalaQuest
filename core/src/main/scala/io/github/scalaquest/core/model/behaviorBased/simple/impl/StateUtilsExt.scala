package io.github.scalaquest.core.model.behaviorBased.simple.impl

import io.github.scalaquest.core.model.{Direction, Model}

/**
 * Integrates some additional functionalities for state inspection and re-generation, by the use of
 * [[monocle.Lens]].
 */
trait StateUtilsExt extends Model {

  /**
   * Add some utility methods for [[Model.State]] implementation.
   * @param state
   *   the state ([[S]])
   */
  implicit class StateUtils(state: S) {
    implicit val s: S = state

    /**
     * Check if an [[Model.Item]] is in Bag.
     * @param item
     *   the item ([[I]]) to check.
     * @return
     *   True if item is in bag, false otherwise.
     */
    def isInBag(item: I): Boolean = state.bag.contains(item)

    /**
     * Check if an [[Model.Item]] is in the actual player location [[Model.Room]].
     * @param item
     *   the item ([[I]]) to check
     * @return
     *   True if item is in location, false otherwise.
     */
    def isInLocation(item: I): Boolean = state.location.items.contains(item)

    /**
     * Check if an [[Model.Item]] is visible. Two possible conditions:
     *   - item is in the actual player location [[Model.Room]].
     *   - item is in bag.
     * @param item
     *   the item ([[I]]) to check.
     * @return
     *   True if item is in location or in bag, false otherwise.
     */
    def isInScope(item: I): Boolean = state.isInLocation(item) || state.isInBag(item)

    /**
     * Check if player have a neighbor Room for a given Direction.
     * @param direction
     *   the given Direction.
     * @return
     *   an Option that contain the neighbor Room ([[RM]]) if present, [[None]] otherwise.
     */
    def locationNeighbor(direction: Direction): Option[RM] = state.location.neighbor(direction)

    /**
     * @return
     *   all the neighbor Rooms ([[RM]]).
     */
    def locationNeighbors: Set[RM] =
      Direction.all.map(state.locationNeighbor(_)).collect({ case Some(room) => room })

    /**
     * @return
     *   all the [[Model.Item]] s present in the player location Room.
     */
    def locationItems: Set[I] = state.location.items

    /**
     * Copy the State [[S]] and add an item to the player location Room.
     * @param item
     *   the [[Model.I]] to add.
     * @return
     *   a new State with the item in the player location Room.
     */
    def copyWithItemInLocation(item: I): S = {
      val stateWithTarget    = itemsLens.modify(_ + (item.ref -> item))(state)
      val currRoomWithTarget = roomItemsLens.modify(_ + item.ref)(state.location)
      roomsLens.modify(_ + (currRoomWithTarget.ref -> currRoomWithTarget))(stateWithTarget)
    }

    /**
     * Copy the State [[S]] and add an item to the player's bag.
     * @param item
     *   the [[Model.I]] to add.
     * @return
     *   a new State with the item in the player's bag.
     */
    def copyWithItemInBag(item: I): S = {
      val stateWithTarget = itemsLens.modify(_ + (item.ref -> item))(state)
      bagLens.modify(_ + item.ref)(stateWithTarget)
    }
  }
}
