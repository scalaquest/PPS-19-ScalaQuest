package io.github.scalaquest.core.model.behaviorBased.impl

import io.github.scalaquest.core.model.{ItemRef, Message, Model, RoomRef}
import monocle.Lens

/**
 * A base trait used to implement all the StdCommon* mixins. Integrates some additional
 * functionalities for state inspection and re-generation, by the use of [[monocle.Lens]].
 */
trait SimpleUtils extends Model {

  implicit def playerBagLens: Lens[S, Set[ItemRef]]
  implicit def matchRoomsLens: Lens[S, Map[RoomRef, RM]]
  implicit def playerLocationLens: Lens[S, RoomRef]
  implicit def itemsLens: Lens[S, Map[ItemRef, I]]
  implicit def roomLens: Lens[RM, Set[ItemRef]]
  implicit def messageLens: Lens[S, Seq[Message]]

  implicit class StateUtils(state: S) {
    def isInBag(item: I): Boolean = state.matchState.player.bag.contains(item.ref)

    def isInCurrentRoom(item: I): Boolean = state.currentRoom.items.contains(item.ref)

    def isInScope(item: I): Boolean = state.isInCurrentRoom(item) || state.isInBag(item)

    def applyReactionIfPresent(maybeReaction: Option[Reaction]): S =
      maybeReaction.fold(state)(_(state))

    def currentRoom: RM = state.matchState.rooms(state.matchState.player.location)

    def itemRefsFromRoomRef(roomRef: RoomRef): Set[ItemRef] =
      state.matchState.rooms.get(roomRef).fold(Set[ItemRef]())(x => x.items)

    def itemsFromRefs(itemRefs: Set[ItemRef]): Set[I] =
      itemRefs.flatMap(k => state.matchState.items.get(k))

    def itemFromRef(itemRef: ItemRef): Option[I] = state.matchState.items.get(itemRef)

    def copyWithItemInLocation(item: I): S = {
      val stateWithTarget    = itemsLens.modify(_ + (item.ref -> item))(state)
      val currRoomWithTarget = roomLens.modify(_ + item.ref)(state.currentRoom)
      matchRoomsLens.modify(_ + (currRoomWithTarget.ref -> currRoomWithTarget))(stateWithTarget)
    }

    def copyWithItemInBag(item: I): S = {
      val stateWithTarget = itemsLens.modify(_ + (item.ref -> item))(state)
      playerBagLens.modify(_ + item.ref)(stateWithTarget)
    }
  }
}
