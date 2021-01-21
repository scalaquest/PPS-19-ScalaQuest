package io.github.scalaquest.core.model.behaviorBased.common

import io.github.scalaquest.core.model.Room
import io.github.scalaquest.core.model.behaviorBased.common.groundBehaviors.CommonGroundBehaviors
import io.github.scalaquest.core.model.behaviorBased.common.behaviors.CommonBehaviors
import io.github.scalaquest.core.model.behaviorBased.common.items.CommonItems
import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import monocle.Lens

/**
 * A base trait used to implement all the StdCommon* mixins. Integrates some additional
 * functionalities for state inspection and re-generation, by the use of [[monocle.Lens]].
 */
trait CommonBase
  extends BehaviorBasedModel
  with CommonGroundBehaviors
  with CommonItems
  with CommonBehaviors {

  implicit def playerBagLens: Lens[S, Set[I]]
  implicit def geographyLens: Lens[S, Map[Room, Set[I]]]
  implicit def playerLocationLens: Lens[S, Room]

  implicit class StateUtils(state: S) {
    def rooms: Set[Room] = state.matchState.geography.keySet

    def isInBag(item: I): Boolean = state.matchState.player.bag.contains(item)

    def isInCurrentRoom(item: I): Boolean =
      state.matchState.geography.get(state.matchState.player.location).exists(_ contains item)

    def isInScope(item: I): Boolean = state.isInCurrentRoom(item) || state.isInBag(item)

    def applyReactionIfPresent(maybeReaction: Option[Reaction]): S =
      maybeReaction.fold(state)(_(state))

    def location: Room = {
      state.matchState.player.location
    }
  }
}
