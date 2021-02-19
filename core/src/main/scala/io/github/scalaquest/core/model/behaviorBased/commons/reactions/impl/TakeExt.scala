package io.github.scalaquest.core.model.behaviorBased.commons.reactions.impl

import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.commons.pushing.CommonMessagesExt
import io.github.scalaquest.core.model.behaviorBased.simple.impl.ReactionUtilsExt

/**
 * A Reaction generated when a player take a takeable Item.
 */
private[reactions] trait TakeExt
  extends BehaviorBasedModel
  with CommonMessagesExt
  with ReactionUtilsExt {

  private[reactions] def take(item: I): Reaction =
    Reaction(
      Update(
        (locationRoomLens composeLens roomItemsLens).modify(_ - item.ref),
        bagLens.modify(_ + item.ref)
      ),
      Messages.Taken(item)
    )
}
