package io.github.scalaquest.core.model.behaviorBased.commons.reactions.impl

import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.commons.pushing.CommonMessagesExt
import io.github.scalaquest.core.model.behaviorBased.simple.impl.ReactionUtilsExt

/**
 * A Reaction generated that return the visible items and the neighbor rooms.
 */
private[reactions] trait InspectLocationExt
  extends BehaviorBasedModel
  with CommonMessagesExt
  with ReactionUtilsExt {

  private[reactions] def inspectLocation: Reaction =
    state => {
      implicit val s: S = state
      Reaction.messages(
        Messages.Inspected(s.location, s.location.items, s.location.neighbors)
      )(state)
    }
}
