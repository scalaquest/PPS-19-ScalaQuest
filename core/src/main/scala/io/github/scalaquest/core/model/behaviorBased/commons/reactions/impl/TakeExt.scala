package io.github.scalaquest.core.model.behaviorBased.commons.reactions.impl

import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.commons.pushing.CommonMessagesExt
import io.github.scalaquest.core.model.behaviorBased.simple.impl.ReactionUtilsExt

/**
 * A mixable trait with a method for a Reaction generated when a player take a takeable Item.
 */
private[reactions] trait TakeExt
  extends BehaviorBasedModel
  with CommonMessagesExt
  with ReactionUtilsExt {

  /**
   * Add to the player's bag the specific item.
   * @param item
   *   the item that have to be taken.
   * @return
   *   a Reaction with:
   *   - removes the item from the actual player room and add it to the player's bag.
   *   - message for an object that is just taken.
   */
  private[reactions] def take(item: I): Reaction =
    Reaction(
      Update(
        (locationRoomLens composeLens roomItemsLens).modify(_ - item.ref),
        bagLens.modify(_ + item.ref)
      ),
      Messages.Taken(item)
    )
}
