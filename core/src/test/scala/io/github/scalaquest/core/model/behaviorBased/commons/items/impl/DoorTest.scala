package io.github.scalaquest.core.model.behaviorBased.commons.items.impl

import io.github.scalaquest.core.TestsUtils
import io.github.scalaquest.core.model.{Direction, ItemDescription}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import TestsUtils.model._
import io.github.scalaquest.core.TestsUtils.model
import io.github.scalaquest.core.model.ItemDescription.dsl.i

class DoorTest extends AnyWordSpec with Matchers {
  val room: model.RM = Room("room")

  "A unlocked Door" should {
    val unlockedDoor = Door(
      ItemDescription("door"),
      RoomLink.closedUnlockedBuilder(room, Direction.North)
    )

    "have a RoomLink accessible from the interface" in {
      unlockedDoor.roomLink shouldBe a[RoomLink]
    }

    "have a open state, initially closed" in {
      unlockedDoor.isOpen shouldBe false
    }
  }

  "A locked Chest" should {
    val (lockedDoor, keyLockedDoor) = Door.createLockedWithKey(
      key = TestsUtils.key,
      doorDesc = i("lockedDoor"),
      endRoom = room,
      endRoomDirection = Direction.North
    )

    "have a RoomLink accessible from the interface" in {
      for {
        openableDoor <- lockedDoor.roomLink.openable toRight fail("Key not correct")
      } yield openableDoor.canBeOpened(Some(keyLockedDoor))(
        TestsUtils.simpleState
      ) shouldBe true

    }

    "be initially is closed" in {
      lockedDoor.isOpen shouldBe false
    }
  }
}
