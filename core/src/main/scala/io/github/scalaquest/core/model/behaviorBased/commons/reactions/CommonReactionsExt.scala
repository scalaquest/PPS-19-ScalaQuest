package io.github.scalaquest.core.model.behaviorBased.commons.reactions

import io.github.scalaquest.core.model.{Direction, ItemRef}
import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.commons.pushing.CommonMessagesExt
import io.github.scalaquest.core.model.behaviorBased.simple.impl.ReactionUtilsExt

/**
 * A mixable trait that contains implementation of common Reactions.
 */
trait CommonReactionsExt extends BehaviorBasedModel with ReactionUtilsExt with CommonMessagesExt {

  /**
   * Companion Object with the effective implementation of the common [[Reaction]] s.
   */
  object Reactions {

    def modifyBag(modifier: Set[ItemRef] => Set[ItemRef]): Reaction =
      Reaction(bagLens.modify(modifier))

    def modifyLocationItems(modifier: Set[ItemRef] => Set[ItemRef]): Reaction =
      Reaction((locationRoomLens composeLens roomItemsLens).modify(modifier))

    def modifyLocation(location: RM): Reaction = Reaction(locationRoomLens.set(location))

    def addDirectionToLocation(direction: Direction, room: RM): Reaction =
      Reaction(
        (locationRoomLens composeLens roomDirectionsLens).modify(_ + (direction -> room.ref))
      )

    /**
     * Create a Reaction that ends the match in two possible scenario:
     *   - player win
     *   - player lose
     * @param win
     *   true if game is finished with a victory, false for a defeat.
     * @return
     *   the Reaction cited above.
     */
    def finishGame(win: Boolean): Reaction =
      for {
        _ <- Reaction(matchEndedLens.set(true))
        s <- Reaction.messages(if (win) Messages.Won else Messages.Lost)
      } yield s

  }
}
