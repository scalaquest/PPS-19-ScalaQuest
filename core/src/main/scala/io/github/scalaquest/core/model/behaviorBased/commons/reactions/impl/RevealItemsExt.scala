package io.github.scalaquest.core.model.behaviorBased.commons.reactions.impl

import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.commons.pushing.CommonMessagesExt
import io.github.scalaquest.core.model.behaviorBased.simple.impl.ReactionUtilsExt

private[reactions] trait RevealItemsExt
  extends BehaviorBasedModel
  with CommonMessagesExt
  with ReactionUtilsExt {

  private[reactions] def revealItems(items: Set[I]): Reaction =
    Reaction(
      Update((locationRoomLens composeLens roomItemsLens).modify(_ ++ items.map(_.ref))),
      Messages.ReversedIntoLocation(items)
    )
}
