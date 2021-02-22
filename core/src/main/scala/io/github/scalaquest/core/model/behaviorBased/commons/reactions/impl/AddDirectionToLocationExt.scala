package io.github.scalaquest.core.model.behaviorBased.commons.reactions.impl

import io.github.scalaquest.core.model.Direction
import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.commons.pushing.CommonMessagesExt
import io.github.scalaquest.core.model.behaviorBased.simple.impl.ReactionUtilsExt

/**
 * A Mixable trait that add to the State a new Reaction that add a new neighbor room to the player's
 * actual location.
 */
private[reactions] trait AddDirectionToLocationExt
  extends BehaviorBasedModel
  with ReactionUtilsExt
  with CommonMessagesExt {

  /**
   * Create a Reaction that add a neighbor room to the actual player location.
   * @param direction
   *   the new visible direction.
   * @param room
   *   the new visible room.
   * @return
   *   the Reaction described above.
   */
  private[reactions] def addDirectionToLocation(direction: Direction, room: RM): Reaction =
    Reaction(
      (locationRoomLens composeLens roomDirectionsLens).modify(_ + (direction -> room.ref))
    )
}
