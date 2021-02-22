package io.github.scalaquest.core.model.behaviorBased.commons.reactions

import io.github.scalaquest.core.model.Direction
import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.commons.reactions.impl._

/**
 * A mixable trait that contains implementation of common Reactions.
 */
trait CommonReactionsExt
  extends BehaviorBasedModel
  with EatExt
  with EnterExt
  with InspectLocationExt
  with FinishGameExt
  with NavigateExt
  with OpenExt
  with TakeExt
  with InspectBagExt
  with RevealItemsExt
  with AddDirectionToLocationExt { self =>

  /**
   * Companion Object with the effective implementation of the common [[Reaction]] s.
   */
  object Reactions {

    /**
     * A Reaction that eat an eatable item.
     * @param item
     *   the item have to be eaten.
     * @return
     *   the Reaction composed by:
     *   - remove the item from room or bag.
     *   - an eaten message.
     */
    def eat(item: I): Reaction = self.eat(item)

    /**
     * A Reaction that move the player in a new room.
     * @param room
     *   the room where player have just entered.
     * @return
     *   a Reaction composed by:
     *   - the new location is the room cited above.
     *   - a message for the navigation of the player.
     */
    def enter(room: RM): Reaction = self.enter(room)

    /**
     * Add to the player's bag the specific item.
     * @param item
     *   that have to be taken.
     * @return
     *   a Reaction with:
     *   - removes the item from the actual player room and add it to the player's bag.
     *   - message for an object that is just taken.
     */
    def take(item: I): Reaction = self.take(item)

    /**
     * The Inspect Location [[Reaction]].
     * @return
     *   all the visible items. The items could be in the player's bag or in the actual location
     *   room.
     */
    def inspectLocation: Reaction = self.inspectLocation

    /**
     * Change player Room.
     * @param room
     *   new player location.
     * @return
     *   the Reaction with the feature cited above.
     */
    def navigate(room: RM): Reaction = self.navigate(room)

    /**
     * Create a Reaction that ends the match in two possible scenario:
     *   - player win
     *   - player lose
     * @param win
     *   true if game is finished with a victory, false for a defeat.
     * @return
     *   the Reaction cited above.
     */
    def finishGame(win: Boolean): Reaction = self.finishGame(win)

    /**
     * The Inspect Bag [[Reaction]].
     * @return
     *   the items contained actually in the bag.
     */
    def inspectBag: Reaction = self.inspectBag

    /**
     * Open a specific Item.
     * @param itemToOpen
     *   is the item that have to be opened.
     * @param requiredKey
     *   if present is the requiredKey to open the item, otherwise key isn't necessary.
     * @return
     */
    def open(
      itemToOpen: I,
      requiredKey: Option[Key]
    ): Reaction = self.open(itemToOpen, requiredKey)

    /**
     * A Reaction that makes visible some items.
     * @param items
     *   the items that will be revealed.
     * @return
     *   the Reaction described above.
     */
    def revealItems(items: Set[I]): Reaction = self.revealItems(items)

    /**
     * Create a Reaction that add a neighbor room to the actual player location.
     * @param direction
     *   the new visible direction.
     * @param room
     *   the new visible room.
     * @return
     *   the Reaction described above.
     */
    def addDirectionToLocation(direction: Direction, room: RM): Reaction =
      self.addDirectionToLocation(direction, room)
  }
}
