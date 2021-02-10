package io.github.scalaquest.core.model.behaviorBased.commons.items.impl

import io.github.scalaquest.core.TestsUtils
import io.github.scalaquest.core.model.{Direction, ItemDescription}
import org.scalatest.wordspec.AnyWordSpec

class DoorTest extends AnyWordSpec {
  import TestsUtils.model._

  "A Door" should {
    val room                = Room("room")
    val roomLinkBehavior    = RoomLink(room, Direction.North, Some(Openable()))
    val additionalBehaviors = Seq(Takeable(), Eatable())
    val door = Door(
      ItemDescription("door"),
      roomLinkBehavior,
      Seq(additionalBehaviors.head, additionalBehaviors(1))
    )

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

    "have an accessor for the open state, initially closed" in {
      assert(!door.isOpen, "The door is open")
    }
  }
}
