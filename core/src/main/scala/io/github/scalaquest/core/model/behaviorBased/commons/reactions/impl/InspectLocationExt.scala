package io.github.scalaquest.core.model.behaviorBased.commons.reactions.impl

import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.commons.pushing.CommonMessagesExt
import io.github.scalaquest.core.model.behaviorBased.simple.impl.StateUtilsExt

private[reactions] trait InspectLocationExt
  extends BehaviorBasedModel
  with StateUtilsExt
  with CommonMessagesExt {

  private[reactions] def inspectLocation: Reaction =
    state => {
      implicit val s: S = state
      messageLens.modify(
        _ :+ Messages.Inspected(state.location, state.location.items, state.location.neighbors)
      )(state)
    }
}
