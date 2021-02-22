package io.github.scalaquest.core.model.behaviorBased.commons.reactions.impl

import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.commons.pushing.CommonMessagesExt
import io.github.scalaquest.core.model.behaviorBased.simple.impl.ReactionUtilsExt

/**
 * mixable trait with a method for a Reaction generated that show all the items contained in the
 * player's bag.
 */
private[reactions] trait InspectBagExt
  extends BehaviorBasedModel
  with CommonMessagesExt
  with ReactionUtilsExt {

  /**
   * @return
   *   the items contained actually in the bag.
   */
  private[reactions] def inspectBag: Reaction = { s =>
    Reaction.messages(Messages.InspectedBag(s.bag))(s)
  }
}
