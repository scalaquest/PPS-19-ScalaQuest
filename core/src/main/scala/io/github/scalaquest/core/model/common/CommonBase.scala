package io.github.scalaquest.core.model.common

import io.github.scalaquest.core.model.Room
import io.github.scalaquest.core.model.common.groundBehaviors.CommonGroundBehaviors
import io.github.scalaquest.core.model.common.behaviors.CommonBehaviors
import io.github.scalaquest.core.model.common.items.CommonItems
import io.github.scalaquest.core.model.std.BehaviorableModel
import monocle.Lens

trait CommonBase
  extends BehaviorableModel
  with CommonGroundBehaviors
  with CommonItems
  with CommonBehaviors {

  implicit def bagLens: Lens[S, Set[I]]
  implicit def itemsLens: Lens[S, Map[Room, Set[I]]]
  implicit def currRoomLens: Lens[S, Room]

  implicit class StateUtils(state: S) {

    def isInBag(item: I): Boolean = state.game.player.bag.contains(item)

    def isInCurrentRoom(item: I): Boolean =
      state.game.itemsInRooms.get(state.game.player.location).exists(_ contains item)

    def isInScope(item: I): Boolean = state.isInCurrentRoom(item) || state.isInBag(item)

    def applyReactionIfPresent(maybeReaction: Option[Reaction]): S =
      maybeReaction.fold(state)(_(state))

    def location: Room = {
      state.game.player.location
    }
  }
}
