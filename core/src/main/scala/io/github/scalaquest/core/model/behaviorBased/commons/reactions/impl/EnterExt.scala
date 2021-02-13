package io.github.scalaquest.core.model.behaviorBased.commons.reactions.impl

import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.commons.pushing.CommonMessagesExt
import io.github.scalaquest.core.model.behaviorBased.simple.impl.StateUtilsExt

private[reactions] trait EnterExt
  extends BehaviorBasedModel
  with StateUtilsExt
  with CommonMessagesExt {

  private[reactions] def enter(room: RM): Reaction = { state =>
    state.applyReactions(
      locationLens.set(room.ref),
      messageLens.modify(_ :+ Messages.Navigated(state.rooms(room.ref)))
    )
  }
}
