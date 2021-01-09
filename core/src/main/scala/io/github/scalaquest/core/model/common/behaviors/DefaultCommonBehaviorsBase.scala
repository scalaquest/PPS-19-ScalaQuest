package io.github.scalaquest.core.model.common.behaviors

import io.github.scalaquest.core.model.Room
import io.github.scalaquest.core.model.common.items.CommonItems
import io.github.scalaquest.core.model.default.BehaviorableModel
import monocle.Lens

trait DefaultCommonBehaviorsBase extends BehaviorableModel with CommonBehaviors with CommonItems {

  implicit def bagLens: Lens[S, Set[I]]
  implicit def itemsLens: Lens[S, Map[Room, Set[I]]]
  implicit def currRoomLens: Lens[S, Room]

  /**
   * Adds some utilities to perform different checks into the state object.
   */
  implicit class StateUtils(state: S) {
    def isInBag(item: I): Boolean = state.game.player.bag.contains(item)

    def isInCurrentRoom(item: I): Boolean =
      state.game.itemsInRooms.collectFirst({ case (room, items) if items.contains(item) => room }).isDefined
  }
}
