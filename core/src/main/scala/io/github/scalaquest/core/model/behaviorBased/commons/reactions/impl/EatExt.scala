package io.github.scalaquest.core.model.behaviorBased.commons.reactions.impl

import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.commons.pushing.CommonMessagesExt
import io.github.scalaquest.core.model.behaviorBased.simple.impl.ReactionUtilsExt

/**
 * A Reaction that eat the item.
 */
private[reactions] trait EatExt
  extends BehaviorBasedModel
  with CommonMessagesExt
  with ReactionUtilsExt {

  private[reactions] def eat(item: I): Reaction =
    Reaction(
      Update(
        (locationRoomLens composeLens roomItemsLens).modify(_ - item.ref),
        bagLens.modify(_ - item.ref)
      ),
      Messages.Eaten(item)
    )
}
