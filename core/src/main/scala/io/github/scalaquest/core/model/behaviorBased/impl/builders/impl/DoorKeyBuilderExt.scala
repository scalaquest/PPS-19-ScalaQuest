package io.github.scalaquest.core.model.behaviorBased.impl.builders.impl

import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.commons.CommonsExt
import io.github.scalaquest.core.model.{ItemDescription, ItemRef}

trait DoorKeyBuilderExt extends BehaviorBasedModel with CommonsExt {

  def doorKeyBuilder(
    keyDesc: ItemDescription,
    keyAddBehaviors: Seq[ItemBehavior] = Seq(),
    doorDesc: ItemDescription,
    endRoom: RM,
    onOpenExtra: Option[Reaction] = None,
    onEnterExtra: Option[Reaction] = None,
    doorAddBehaviors: Seq[ItemBehavior] = Seq()
  ): (Door, Key) = {

    val key = SimpleKey(keyDesc, ItemRef(keyDesc), keyAddBehaviors: _*)

    val door = SimpleDoor(
      description = doorDesc,
      ref = ItemRef(doorDesc),
      SimpleRoomLink(
        endRoom = endRoom,
        openable = Some(SimpleOpenable(requiredKey = Some(key), onOpenExtra = onOpenExtra)),
        onEnterExtra = onEnterExtra
      ),
      doorAddBehaviors: _*
    )

    (door, key)
  }
}
