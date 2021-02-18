package io.github.scalaquest.core.model.behaviorBased.commons.reactions.impl

import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.commons.pushing.CommonMessagesExt
import io.github.scalaquest.core.model.behaviorBased.simple.impl.{ReactionUtilsExt, StateUtilsExt}

/**
 * A Reaction generated that return items contained in the bag.
 */
private[reactions] trait InspectBagExt
  extends BehaviorBasedModel
  with StateUtilsExt
  with CommonMessagesExt
  with ReactionUtilsExt {

  private[reactions] def inspectBag: Reaction = { s =>
    Reaction.messages(Messages.InspectedBag(s.bag))(s)
  }
}
