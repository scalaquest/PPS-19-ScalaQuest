package io.github.scalaquest.core.model.behaviorBased.simple.builders

import io.github.scalaquest.core.TestsUtils
import io.github.scalaquest.core.model.{Direction, ItemDescription}
import org.scalatest.wordspec.AnyWordSpec

class DoorKeyBuilderExtTest extends AnyWordSpec {
  import TestsUtils._
  import TestsUtils.model._

  "A DoorKeyBuilder" should {
    val (door, key) = doorKeyBuilder(
      keyDesc = ItemDescription("key"),
      doorDesc = ItemDescription("door"),
      endRoom = targetRoom,
      endRoomDirection = Direction.North
    )

    "return a tuple with a working key-door pair" in {
      assert(door.roomLink.openable.get.requiredKey.get == key, "The key do not matches")
    }
  }
}
