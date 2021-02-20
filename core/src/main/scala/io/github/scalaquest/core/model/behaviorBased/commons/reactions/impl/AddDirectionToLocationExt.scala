package io.github.scalaquest.core.model.behaviorBased.commons.reactions.impl

import io.github.scalaquest.core.model.Direction
import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.commons.pushing.CommonMessagesExt
import io.github.scalaquest.core.model.behaviorBased.simple.impl.ReactionUtilsExt

private[reactions] trait AddDirectionToLocationExt
  extends BehaviorBasedModel
  with ReactionUtilsExt
  with CommonMessagesExt {

  private[reactions] def addDirectionToLocation(direction: Direction, room: RM): Reaction =
    Reaction(
      (locationRoomLens composeLens roomDirectionsLens).modify(_ + (direction -> room.ref))
    )
}
