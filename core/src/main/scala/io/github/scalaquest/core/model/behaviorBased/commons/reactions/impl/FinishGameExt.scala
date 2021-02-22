package io.github.scalaquest.core.model.behaviorBased.commons.reactions.impl

import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.commons.pushing.CommonMessagesExt
import io.github.scalaquest.core.model.behaviorBased.simple.impl.ReactionUtilsExt

/**
 * A mixable trait that add a Reaction used to finish the Game. Two possible scenarios:
 *   - player win
 *   - player lose
 */
private[reactions] trait FinishGameExt
  extends BehaviorBasedModel
  with CommonMessagesExt
  with ReactionUtilsExt {

  /**
   * Create a Reaction that ends the match in two possible scenario:
   *   - player win
   *   - player lose
   * @param win
   *   true if game is finished with a victory, false for a defeat.
   * @return
   *   the Reaction cited above.
   */
  private[reactions] def finishGame(win: Boolean): Reaction =
    Reaction(
      matchEndedLens.set(true),
      if (win) Messages.Won else Messages.Lost
    )
}
