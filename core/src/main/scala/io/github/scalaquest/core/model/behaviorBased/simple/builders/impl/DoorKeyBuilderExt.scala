package io.github.scalaquest.core.model.behaviorBased.simple.builders.impl

import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.commons.CommonsExt
import io.github.scalaquest.core.model.{Direction, ItemDescription, ItemRef}

trait DoorKeyBuilderExt extends BehaviorBasedModel with CommonsExt {

  def doorKeyBuilder(
    keyDesc: ItemDescription,
    keyAddBehaviors: Seq[ItemBehavior] = Seq(),
    consumeKey: Boolean = false,
    doorDesc: ItemDescription,
    endRoom: RM,
    endRoomDirection: Direction,
    onOpenExtra: Option[Reaction] = None,
    onEnterExtra: Option[Reaction] = None,
    doorAddBehaviors: Seq[ItemBehavior] = Seq()
  ): (Door, Key) = {

    val key = Key(keyDesc, keyAddBehaviors)

    val door = Door(
      description = doorDesc,
      RoomLink(
        endRoom = endRoom,
        endRoomDirection = endRoomDirection,
        openable = Some(
          Openable(
            consumeKey = consumeKey,
            requiredKey = Some(key),
            onOpenExtra = onOpenExtra
          )
        ),
        onEnterExtra = onEnterExtra
      ),
      additionalBehaviors = doorAddBehaviors
    )

    (door, key)
  }
}
