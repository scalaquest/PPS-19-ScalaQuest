package io.github.scalaquest.core.model.behaviorBased.commons.reactions.impl

import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.commons.pushing.CommonMessagesExt
import io.github.scalaquest.core.model.behaviorBased.simple.impl.ReactionUtilsExt

/**
 * A mixable trait with a method for a Reaction that eat the eatable item.
 */
private[reactions] trait EatExt
  extends BehaviorBasedModel
  with CommonMessagesExt
  with ReactionUtilsExt {

  /**
   * A Reaction that eat an eatable item.
   * @param item
   *   the item have to be eaten.
   * @return
   *   the Reaction composed by:
   *   - remove the item from room or bag.
   *   - an eaten message.
   */
  private[reactions] def eat(item: I): Reaction =
    Reaction(
      Update(
        (locationRoomLens composeLens roomItemsLens).modify(_ - item.ref),
        bagLens.modify(_ - item.ref)
      ),
      Messages.Eaten(item)
    )
}
