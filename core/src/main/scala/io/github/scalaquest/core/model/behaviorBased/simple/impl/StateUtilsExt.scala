/*
 * Copyright (c) 2021 ScalaQuest Team.
 *
 * This file is part of ScalaQuest, and is distributed under the terms of the MIT License as
 * described in the file LICENSE in the ScalaQuest distribution's top directory.
 */

package io.github.scalaquest.core.model.behaviorBased.simple.impl

import io.github.scalaquest.core.model.{Direction, Model}

/**
 * Integrates some additional functionalities for state inspection and re-generation, by the use of
 * [[monocle.Lens]].
 */
trait StateUtilsExt extends Model {

  /**
   * Add some utility methods for [[State]] implementation.
   * @param state
   *   A state ([[S]])
   */
  implicit class StateUtils(state: S) {
    implicit val s: S = state

    /**
     * Check if an [[Item]] is in Bag.
     * @param item
     *   The item ([[I]]) to check.
     * @return
     *   True if item is in bag, false otherwise.
     */
    def isInBag(item: I): Boolean = state.bag.contains(item)

    /**
     * Check if an [[Item]] is in the actual player location [[Room]].
     * @param item
     *   The item ([[I]]) to check.
     * @return
     *   True if item is in location, false otherwise.
     */
    def isInLocation(item: I): Boolean = state.location.items.contains(item)

    /**
     * Check if an [[Item]] is visible. Two possible conditions:
     *   - item is in the actual player location [[Room]].
     *   - item is in bag.
     * @param item
     *   the item ([[I]]) to check.
     * @return
     *   True if item is in location or in bag, false otherwise.
     */
    def isInScope(item: I): Boolean = state.isInLocation(item) || state.isInBag(item)

    /**
     * Check if player have a neighbor [[Room]] for a given Direction.
     * @param direction
     *   The given [[Direction]].
     * @return
     *   [[Option]] that contain the neighbor Room ([[RM]]) if present, [[None]] otherwise.
     */
    def locationNeighbor(direction: Direction): Option[RM] = state.location.neighbor(direction)

    /**
     * Responds with all the [[Item]] s present in the player location [[Room]].
     * @return
     *   All the [[Item]] s present in the player location [[Room]].
     */
    def locationItems: Set[I] = state.location.items

    /**
     * Copy the State [[S]] and add an item to the player location Room.
     * @param item
     *   The <b>item</b> [[I]] to add.
     * @return
     *   A new <b>State</b> with the <b>item</b> added into the player's location.
     */
    def copyWithItemInLocation(item: I): S = {
      val stateWithTarget    = itemsLens.modify(_ + (item.ref -> item))(state)
      val currRoomWithTarget = roomItemsLens.modify(_ + item.ref)(state.location)
      roomsLens.modify(_ + (currRoomWithTarget.ref -> currRoomWithTarget))(stateWithTarget)
    }

    /**
     * Copy the State [[S]] and add an item to the player's bag.
     * @param item
     *   The <b>item</b> [[I]] to add to the State.
     * @return
     *   The new <b>State</b> with the <b>item</b> in the player's <b>bag</b>.
     */
    def copyWithItemInBag(item: I): S = {
      val stateWithTarget = itemsLens.modify(_ + (item.ref -> item))(state)
      bagLens.modify(_ + item.ref)(stateWithTarget)
    }
  }
}
