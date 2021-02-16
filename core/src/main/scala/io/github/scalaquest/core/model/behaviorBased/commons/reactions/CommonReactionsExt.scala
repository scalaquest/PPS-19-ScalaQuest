package io.github.scalaquest.core.model.behaviorBased.commons.reactions

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
  with EmptyExt
  with TakeExt
  with InspectBagExt { self =>

  /**
   * Companioni Object with the effective implementation of the common [[Reaction]] s.
   */
  object Reactions {

    /**
     * The Eatable [[Reaction]].
     * @param item
     *   that have to be eaten.
     * @return
     *   Eaten [[Reaction]]
     */
    def eat(item: I): Reaction = self.eat(item)

    def empty: Reaction = self.empty

    /**
     * The RoomLink [[Reaction]].
     * @param room
     *   that have to be reached.
     * @return
     *   RoomLink [[Reaction]]
     */
    def enter(room: RM): Reaction = self.enter(room)

    /**
     * The Takeable [[Reaction]].
     * @param item
     *   that have to be taken.
     * @return
     *   Taken [[Reaction]]
     */
    def take(item: I): Reaction = self.take(item)

    /**
     * The Inspect Location [[Reaction]].
     * @return
     *   The Inspect Location [[Reaction]].
     */
    def inspectLocation: Reaction = self.inspectLocation

    /**
     * The Navigate [[Reaction]].
     * @param room
     *   that have to be reached.
     * @return
     *   Navigate [[Reaction]]
     */
    def navigate(room: RM): Reaction = self.navigate(room)

    /**
     * The Finish Game [[Reaction]].
     * @param win
     *   true if player have won the game, false otherwise.
     * @return
     *   The FinishGame [[Reaction]]
     */
    def finishGame(win: Boolean): Reaction = self.finishGame(win)

    /**
     * The Inspect Bag [[Reaction]].
     * @return
     *   The Inspect Bag [[Reaction]].
     */
    def inspectBag: Reaction = self.inspectBag

    /**
     * The Openable [[Reaction]].
     * @param itemToOpen
     *   is the item that have to be opened.
     * @param requiredKey
     *   if present is the requiredKey to open the item, otherwise key isn't necessary.
     * @param iskeyConsumable
     *   is true if key after his use have to be destroyed, false otherwise.
     * @return
     */
    def open(
      itemToOpen: I,
      requiredKey: Option[Key],
      iskeyConsumable: Boolean
    ): Reaction = self.open(itemToOpen, requiredKey, iskeyConsumable)
  }
}
