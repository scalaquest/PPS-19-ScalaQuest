package io.github.scalaquest.core.model.behaviorBased.commons.reactions.impl

import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.commons.pushing.CommonMessagesExt
import io.github.scalaquest.core.model.behaviorBased.simple.impl.ReactionUtilsExt

/**
 * A Reaction generated when a player go in a nearby room.
 */
private[reactions] trait NavigateExt
  extends BehaviorBasedModel
  with CommonMessagesExt
  with ReactionUtilsExt {

  private[reactions] def navigate(room: RM): Reaction =
    Reaction(
      locationLens.set(room.ref),
      Messages.Navigated(room)
    )
}
