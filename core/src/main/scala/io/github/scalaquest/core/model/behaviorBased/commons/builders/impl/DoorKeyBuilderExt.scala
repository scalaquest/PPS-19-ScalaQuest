package io.github.scalaquest.core.model.behaviorBased.commons.builders.impl

import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.commons.CommonsExt
import io.github.scalaquest.core.model.{Direction, ItemDescription}

trait DoorKeyBuilderExt extends BehaviorBasedModel with CommonsExt {

  def doorKeyBuilder(
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
