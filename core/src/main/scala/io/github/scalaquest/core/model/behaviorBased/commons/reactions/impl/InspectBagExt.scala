package io.github.scalaquest.core.model.behaviorBased.commons.reactions.impl

import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.commons.pushing.CommonMessagesExt
import io.github.scalaquest.core.model.behaviorBased.simple.impl.StateUtilsExt

private[reactions] trait InspectBagExt
  extends BehaviorBasedModel
  with StateUtilsExt
  with CommonMessagesExt {

  private[reactions] def inspectBag: Reaction =
    state => {
      messageLens.modify(
        _ :+ Messages.InspectedBag(state.bag)
      )(state)
    }
}
