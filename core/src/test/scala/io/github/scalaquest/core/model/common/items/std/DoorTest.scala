package io.github.scalaquest.core.model.common.items.std

import io.github.scalaquest.core.model.Direction.Direction
import io.github.scalaquest.core.model.Room
import io.github.scalaquest.core.model.std.StdModel.{RoomLink, Openable, Eatable, Takeable, Door}
import org.scalatest.wordspec.AnyWordSpec

class DoorTest extends AnyWordSpec {
  "A Door" when {
    val room: Room          = Room("room", () => Map[Direction, Room]())
    val roomLinkBehavior    = RoomLink(room, Some(Openable()))
    val additionalBehaviors = Seq(Takeable(), Eatable())
    val door                = Door("Door", roomLinkBehavior, additionalBehaviors.head, additionalBehaviors(1))

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
