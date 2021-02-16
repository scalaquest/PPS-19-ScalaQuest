package io.github.scalaquest.core.model.behaviorBased.commons.reactions.impl

import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.commons.pushing.CommonMessagesExt
import io.github.scalaquest.core.model.behaviorBased.simple.impl.StateUtilsExt

/**
 * A Reaction used to finish the Game. Two possible scenarios:
 *   - player win
 *   - player lose
 */
private[reactions] trait FinishGameExt
  extends BehaviorBasedModel
  with StateUtilsExt
  with CommonMessagesExt {

  private[reactions] def finishGame(win: Boolean): Reaction =
    _.applyReactions(
      matchEndedLens.set(true),
      messageLens.modify(_ :+ (if (win) Messages.Won else Messages.Lost))
    )
}
