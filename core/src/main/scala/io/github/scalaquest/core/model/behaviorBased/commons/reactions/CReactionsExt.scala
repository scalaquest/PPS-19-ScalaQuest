package io.github.scalaquest.core.model.behaviorBased.commons.reactions

import io.github.scalaquest.core.model.{Direction, ItemRef, Message, RoomRef}
import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.commons.pushing.CMessagesExt
import io.github.scalaquest.core.model.behaviorBased.simple.impl.ReactionUtilsExt

/**
 * A mixable trait that contains some common <b>Reactions</b>.
 */
trait CReactionsExt extends BehaviorBasedModel with ReactionUtilsExt with CMessagesExt {

  /**
   * Companion Object with the effective implementation of the common [[Reaction]] s.
   */
  object CReactions {

    /**
     * <b>Reaction</b> that modifies the content of the player's bag, following the instructions
     * from the given function.
     * @param modifier
     *   A function that indicates how to modify the bag.
     * @return
     *   <b>Reaction</b> that modifies the content of the player's bag.
     */
    def modifyBag(modifier: Set[ItemRef] => Set[ItemRef]): Reaction =
      Reaction(bagLens.modify(modifier))

    /**
     * <b>Reaction</b> that modifies the items contained into the player's location, following the
     * instructions from the given function.
     * @param modifier
     *   A function that indicates how to modify the items contained into the player's location.
     * @return
     *   <b>Reaction</b> that modifies the items contained into the player's location.
     */
    def modifyLocationItems(modifier: Set[ItemRef] => Set[ItemRef]): Reaction =
      Reaction((locationRoomLens composeLens roomItemsLens).modify(modifier))

    /**
     * <b>Reaction</b> that changes the location of the player, given a <b>RoomRef</b>.
     * @param location
     *   The new location of the player, as a <b>RoomRef</b>.
     * @return
     *   <b>Reaction</b> that switches the location of the player.
     */
    def switchLocation(location: RoomRef): Reaction = Reaction(locationLens.set(location))

    /**
     * <b>Reaction</b> that changes the location of the player.
     * @param location
     *   The new location of the player.
     * @return
     *   <b>Reaction</b> that switches the location of the player.
     */
    def switchLocation(location: RM): Reaction = switchLocation(location.ref)

    /**
     * <b>Reaction</b> that adds a <b>Message</b> to the ones to be returned.
     * @param message
     *   The new <b>Message</b>.
     * @return
     *   <b>Reaction</b> that adds a <b>Message</b> to the ones to be returned.
     */
    def addMessage(message: Message): Reaction = Reaction.messages(message)

    /**
     * <b>Reaction</b> that adds a neighbor <b>Room</b> to the actual player location.
     * @param direction
     *   the new visible direction.
     * @param room
     *   the new visible room.
     * @return
     *   the Reaction described above.
     */
    def addDirectionToLocation(direction: Direction, room: RM): Reaction =
      Reaction(
        (locationRoomLens composeLens roomDirectionsLens).modify(_ + (direction -> room.ref))
      )

    /**
     * <b>Reaction</b> that ends the match in two possible scenario:
     *   - The player wins
     *   - The player loses
     * @param win
     *   True if game is finished with a victory, False for a defeat.
     * @return
     *   the Reaction cited above.
     */
    def finishGame(win: Boolean): Reaction =
      for {
        _ <- Reaction(matchEndedLens.set(true))
        s <- Reaction.messages(if (win) CMessages.Won else CMessages.Lost)
      } yield s
  }
}
