package io.github.scalaquest.core.model.behaviorBased.common.items.impl

import io.github.scalaquest.core.model.{ItemDescription, ItemRef}
import io.github.scalaquest.core.model.behaviorBased.impl.SimpleModel.{
  SimpleDoor,
  SimpleEatable,
  SimpleOpenable,
  SimpleRoomLink,
  SimpleTakeable,
  Room
}
import org.scalatest.wordspec.AnyWordSpec

class SimpleDoorTest extends AnyWordSpec {
  "A Door" when {
    val room = Room(
      "room",
      Map(),
      Set()
    )
    val roomLinkBehavior    = SimpleRoomLink(room, Some(SimpleOpenable()))
    val additionalBehaviors = Seq(SimpleTakeable(), SimpleEatable())
    val door =
      SimpleDoor(
        ItemDescription("door"),
        new ItemRef {},
        roomLinkBehavior,
        additionalBehaviors.head,
        additionalBehaviors(1)
      )

    "instantiated" should {
      "take a RoomLink Behavior and save it as the first behavior" in {
        assert(
          door.behaviors.head == door.doorBehavior,
          "the roomLink is not in the first position."
        )
      }
      "take additional behaviors as subsequent to the door behavior" in {
        assert(
          door.behaviors.tail == additionalBehaviors,
          "the additional behaviors are not in the right place."
        )
      }
    }
  }
}
