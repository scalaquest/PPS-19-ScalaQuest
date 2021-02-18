package io.github.scalaquest.core.model.behaviorBased.commons.reactions.impl

import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.commons.pushing.CommonMessagesExt
import io.github.scalaquest.core.model.behaviorBased.simple.impl.{ReactionUtilsExt, StateUtilsExt}

/**
 * A Reaction used to finish the Game. Two possible scenarios:
 *   - player win
 *   - player lose
 */
private[reactions] trait FinishGameExt
  extends BehaviorBasedModel
  with StateUtilsExt
  with CommonMessagesExt
  with ReactionUtilsExt {

  private[reactions] def finishGame(win: Boolean): Reaction =
    Reaction(
      matchEndedLens.set(true),
      if (win) Messages.Won else Messages.Lost
    )
}
