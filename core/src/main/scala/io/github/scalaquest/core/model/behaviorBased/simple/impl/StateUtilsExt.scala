package io.github.scalaquest.core.model.behaviorBased.simple.impl

import io.github.scalaquest.core.model.{Direction, Model}

/**
 * Integrates some additional functionalities for state inspection and re-generation, by the use of
 * [[monocle.Lens]].
 */
trait StateUtilsExt extends Model {

  implicit class StateUtils(state: S) {
    implicit val s: S = state

    def isInBag(item: I): Boolean = state.bag.contains(item)

    def isInLocation(item: I): Boolean = state.location.items.contains(item)

    def isInScope(item: I): Boolean = state.isInLocation(item) || state.isInBag(item)

    // shortcuts
    def locationNeighbor(direction: Direction): Option[RM] = state.location.neighbor(direction)

    def locationNeighbors: Set[RM] =
      Direction.all.map(d => state.locationNeighbor(d)).collect({ case Some(room) => room })

    def locationItems: Set[I] = state.location.items

    def copyWithItemInLocation(item: I): S = {
      val stateWithTarget    = itemsLens.modify(_ + (item.ref -> item))(state)
      val currRoomWithTarget = roomItemsLens.modify(_ + item.ref)(state.location)
      roomsLens.modify(_ + (currRoomWithTarget.ref -> currRoomWithTarget))(stateWithTarget)
    }

    def copyWithItemInBag(item: I): S = {
      val stateWithTarget = itemsLens.modify(_ + (item.ref -> item))(state)
      bagLens.modify(_ + item.ref)(stateWithTarget)
    }
  }
}
