package io.github.scalaquest.core.model.behaviorBased.common

import io.github.scalaquest.core.model.{ItemRef, Room, RoomRef}
import io.github.scalaquest.core.model.behaviorBased.common.groundBehaviors.CommonGroundBehaviors
import io.github.scalaquest.core.model.behaviorBased.common.itemBehaviors.CommonBehaviors
import io.github.scalaquest.core.model.behaviorBased.common.items.CommonItems
import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import monocle.Lens
import monocle.macros.GenLens

/**
 * A base trait used to implement all the StdCommon* mixins. Integrates some additional
 * functionalities for state inspection and re-generation, by the use of [[monocle.Lens]].
 */
trait CommonBase
  extends BehaviorBasedModel
  with CommonGroundBehaviors
  with CommonItems
  with CommonBehaviors {

  implicit def playerBagLens: Lens[S, Set[ItemRef]]
  implicit def matchRoomsLens: Lens[S, Set[Room]]
  implicit def playerLocationLens: Lens[S, RoomRef]
  implicit def itemsLens: Lens[S, Set[I]]
  implicit def roomLens: Lens[Room, Set[ItemRef]]

  implicit class StateUtils(state: S) {
    def isInBag(item: I): Boolean = state.matchState.player.bag.contains(item.ref)

    def isInCurrentRoom(item: I): Boolean =
      state.matchState.rooms
        .collectFirst({ case room if room.ref == state.matchState.player.location => room.items })
        .exists(_ contains item.ref)

    def isInScope(item: I): Boolean = state.isInCurrentRoom(item) || state.isInBag(item)

    def applyReactionIfPresent(maybeReaction: Option[Reaction]): S =
      maybeReaction.fold(state)(_(state))

    def currentRoom: Room = {
      state.matchState.rooms
        .collectFirst({ case room if room.ref == state.matchState.player.location => room })
        .get
    }

    def itemRefsFromRoomRef(roomRef: RoomRef): Set[ItemRef] =
      state.matchState.rooms
        .collectFirst({ case room if room.ref == roomRef => room.items })
        .getOrElse(Set())

    def itemsFromRefs(itemRefs: Set[ItemRef]): Set[I] = {
      itemRefs.map(ref => itemFromRef(ref)).collect({ case Some(item) => item })
    }

    def itemFromRef(itemRef: ItemRef): Option[I] =
      state.matchState.items.collectFirst({ case item if item.ref == itemRef => item })

    def copyWithItemInLocation(item: I): S = {
      val stateWithTarget    = itemsLens.modify(_ + item)(state)
      val currRoomWithTarget = roomLens.modify(_ + item.ref)(state.currentRoom)
      matchRoomsLens.modify(_ + currRoomWithTarget)(stateWithTarget)
    }

  }
}
