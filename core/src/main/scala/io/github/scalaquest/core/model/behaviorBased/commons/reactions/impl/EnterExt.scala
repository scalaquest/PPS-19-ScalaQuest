package io.github.scalaquest.core.model.behaviorBased.commons.reactions.impl

import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.commons.pushing.CommonMessagesExt
import io.github.scalaquest.core.model.behaviorBased.simple.impl.{ReactionUtilsExt, StateUtilsExt}

/**
 * A mixable trait with a method for a Reaction used to enter in a new Room.
 */
private[reactions] trait EnterExt
  extends BehaviorBasedModel
  with StateUtilsExt
  with CommonMessagesExt
  with ReactionUtilsExt {

  /**
   * A Reaction that move the player in a new room.
   * @param room
   *   the room where player have just entered.
   * @return
   *   a Reaction composed by:
   *   - the new location is the room cited above.
   *   - a message for the navigation of the player.
   */
  private[reactions] def enter(room: RM): Reaction =
    Reaction(
      locationRoomLens.set(room),
      Messages.Navigated(room)
    )
}
