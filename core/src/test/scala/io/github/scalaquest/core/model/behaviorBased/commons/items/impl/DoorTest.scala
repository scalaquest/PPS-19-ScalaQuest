package io.github.scalaquest.core.model.behaviorBased.commons.items.impl

import io.github.scalaquest.core.TestsUtils
import io.github.scalaquest.core.model.{Direction, ItemDescription}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import TestsUtils.model._

class DoorTest extends AnyWordSpec with Matchers {

  "A Door" should {
    val room = Room("room")
    val door = Door(
      ItemDescription("door"),
      RoomLink.closedUnlockedBuilder(room, Direction.North)
    )

    "have a RoomLink accessible from the interface" in {
      door.roomLink shouldBe a[RoomLink]
    }

    "have a open state, initially closed" in {
      door.isOpen shouldBe false
    }
  }
}
