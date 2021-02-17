package io.github.scalaquest.core.model.behaviorBased.commons.reactions.impl

import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.commons.pushing.CommonMessagesExt
import io.github.scalaquest.core.model.behaviorBased.simple.impl.StateUtilsExt

/**
 * A Reaction generated when a player go in a nearby room.
 */
private[reactions] trait NavigateExt
  extends BehaviorBasedModel
  with StateUtilsExt
  with CommonMessagesExt {

  private[reactions] def navigate(room: RM): Reaction =
    _.applyReactions(
      locationLens.set(room.ref),
      messageLens.modify(_ :+ Messages.Navigated(room))
    )
}
