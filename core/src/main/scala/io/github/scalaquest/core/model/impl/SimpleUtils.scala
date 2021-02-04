package io.github.scalaquest.core.model.impl

import io.github.scalaquest.core.model.{Direction, ItemRef, Message, Model, RoomRef}
import monocle.Lens

/**
 * Integrates some additional functionalities for state inspection and re-generation, by the use of
 * [[monocle.Lens]].
 */
trait SimpleUtils extends Model {

  implicit def playerBagLens: Lens[S, Set[ItemRef]]
  implicit def matchRoomsLens: Lens[S, Map[RoomRef, RM]]
  implicit def playerLocationLens: Lens[S, RoomRef]
  implicit def itemsLens: Lens[S, Map[ItemRef, I]]
  implicit def roomLens: Lens[RM, Set[ItemRef]]
  implicit def roomDirectionsLens: Lens[RM, Map[Direction, RoomRef]]

  implicit class StateUtils(state: S) {
    def isInBag(item: I): Boolean = state.matchState.player.bag.contains(item.ref)

    def isInCurrentRoom(item: I): Boolean = state.currentRoom.items.contains(item.ref)

    def isInScope(item: I): Boolean = state.isInCurrentRoom(item) || state.isInBag(item)

    def currentRoom: RM = state.matchState.rooms(state.matchState.player.location)

    def itemRefsFromRoomRef(roomRef: RoomRef): Set[ItemRef] =
      state.matchState.rooms.get(roomRef).fold(Set[ItemRef]())(_.items)

    def itemsFromRefs(itemRefs: Set[ItemRef]): Set[I] =
      itemRefs.flatMap(state.matchState.items.get(_))

    def itemFromRef(itemRef: ItemRef): Option[I] = state.matchState.items.get(itemRef)

    def roomFromRef(roomRef: RoomRef): Option[RM] = state.matchState.rooms.get(roomRef)

    def copyWithItemInLocation(item: I): S = {
      val stateWithTarget    = itemsLens.modify(_ + (item.ref -> item))(state)
      val currRoomWithTarget = roomLens.modify(_ + item.ref)(state.currentRoom)
      matchRoomsLens.modify(_ + (currRoomWithTarget.ref -> currRoomWithTarget))(stateWithTarget)
    }

    def copyWithItemInBag(item: I): S = {
      val stateWithTarget = itemsLens.modify(_ + (item.ref -> item))(state)
      playerBagLens.modify(_ + item.ref)(stateWithTarget)
    }

    def applyReactions(reactions: Reaction*): S = Function.chain(reactions.toList)(this.state)

  }
}
