package io.github.scalaquest.core.model.behaviorBased.commons.reactions.impl

import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.commons.pushing.CommonMessagesExt
import io.github.scalaquest.core.model.behaviorBased.simple.impl.{ReactionUtilsExt, StateUtilsExt}

/**
 * A Reaction generated that return the visible items and the neighbor rooms.
 */
private[reactions] trait InspectLocationExt
  extends BehaviorBasedModel
  with StateUtilsExt
  with CommonMessagesExt
  with ReactionUtilsExt {

  private[reactions] def inspectLocation: Reaction =
    state => {
      implicit val s: S = state
      Reaction.messages(
        Messages.Inspected(state.location, state.location.items, state.location.neighbors)
      )(state)
    }
}
