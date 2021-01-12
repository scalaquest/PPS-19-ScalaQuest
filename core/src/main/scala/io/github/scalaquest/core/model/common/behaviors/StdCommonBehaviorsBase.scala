package io.github.scalaquest.core.model.common.behaviors

import io.github.scalaquest.core.model.Room
import io.github.scalaquest.core.model.common.items.CommonItems
import io.github.scalaquest.core.model.Model
import io.github.scalaquest.core.model.std.BehaviorableModel
import monocle.Lens

/**
 * Base trait for the implementation of the [[BehaviorableModel.Behavior]]s included into [[CommonBehaviors.CommonBehaviors]], provided by
 * default by ScalaQuest Core.
 */
trait StdCommonBehaviorsBase extends BehaviorableModel with CommonBehaviors with CommonItems {

  /**
   * A [[Lens]] used by the implementation to access the bag of the user.
   *
   * The storyteller should provide a Lens implementation as an implicit, as this implementation exploits this
   * mechanism internally.
   * @return A [[Lens]] to access the bag of the user.
   */
  implicit def bagLens: Lens[S, Set[I]]

  /**
   * A [[Lens]] used by the implementation to access the Item configuration of each Room.
   *
   * The storyteller should provide a Lens implementation as an implicit, as this implementation exploits this
   * mechanism internally.
   * @return A [[Lens]] to access the Item configuration of each Room.
   */
  implicit def itemsLens: Lens[S, Map[Room, Set[I]]]

  /**
   * A [[Lens]] used by the implementation to access the current Room.
   *
   * The storyteller should provide a Lens implementation as an implicit, as this implementation exploits this
   * mechanism internally.
   * @return A [[Lens]] to access the current Room.
   */
  implicit def currRoomLens: Lens[S, Room]

  /**
   * This implicit class "automagically" adds some utility methods to perform different checks
   * into the [[Model.State]] object.
   * @param state The [[Model.State]] to whom supply the utils.
   */
  implicit class StateUtils(state: S) {

    /**
     * Checks whether the given [[Item]] is contained into the bag of the player.
     * @param item The  [[Item]] to check.
     * @return True if the [[Item]] is contained into the bag, False otherwise.
     */
    def isInBag(item: I): Boolean = state.game.player.bag.contains(item)

    /**
     * Checks whether the given [[Item]] is contained into the current Room.
     * @param item The [[Item]] to check.
     * @return True if the [[Item]] is contained into the current Room, False otherwise.
     */
    def isInCurrentRoom(item: I): Boolean =
      state.game.itemsInRooms.get(state.game.player.location).exists(_ contains item)

    /**
     * The "scope" it the set of [[Item]]s currently reachable by the player, e.g. all the items in the current Room or
     * into the bag.
     * @param item The Item to check.
     * @return True if the item is in scope, False otherwise.
     */
    def isInScope(item: I): Boolean = state.isInCurrentRoom(item) || state.isInBag(item)

    /**
     * Tries to apply the given reaction to the [[State]]
     * @param maybeReaction An [[Option]] that could contain a [[Reaction]].
     * @return 'this' if maybeReaction is None, 'this' modified by the given [[Reaction]], otherwise.
     */
    def applyReactionIfPresent(maybeReaction: Option[Reaction]): S = maybeReaction.fold(state)(_(state))
  }
}
