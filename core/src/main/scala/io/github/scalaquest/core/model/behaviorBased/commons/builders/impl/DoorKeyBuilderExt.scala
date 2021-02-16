package io.github.scalaquest.core.model.behaviorBased.commons.builders.impl

import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.commons.items.CommonItemsExt
import io.github.scalaquest.core.model.{Direction, ItemDescription}

/**
 * A convenient implementation for a room, and if present, a key connected to it.
 */
trait DoorKeyBuilderExt extends BehaviorBasedModel with CommonItemsExt {

  def lockedDoorBuilder(
    keyDesc: ItemDescription,
    keyAddBehaviorsBuilders: Seq[I => ItemBehavior] = Seq.empty,
    consumeKey: Boolean = false,
    doorDesc: ItemDescription,
    endRoom: RM,
    endRoomDirection: Direction,
    onOpenExtra: Option[Reaction] = None,
    onEnterExtra: Option[Reaction] = None,
    doorAddBehaviorsBuilders: Seq[I => ItemBehavior] = Seq.empty
  ): (Door, Key) = {

    val key = Key(keyDesc, keyAddBehaviorsBuilders)

    val door = Door(
      description = doorDesc,
      RoomLink.builder(
        endRoom = endRoom,
        endRoomDirection = endRoomDirection,
        openableBuilder = Some(
          Openable.builder(
            consumeKey = consumeKey,
            requiredKey = Some(key),
            onOpenExtra = onOpenExtra
          )
        ),
        onEnterExtra = onEnterExtra
      ),
      addBehaviorsBuilders = doorAddBehaviorsBuilders
    )

    (door, key)
  }
}
