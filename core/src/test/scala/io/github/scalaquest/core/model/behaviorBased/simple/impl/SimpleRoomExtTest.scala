package io.github.scalaquest.core.model.behaviorBased.simple.impl

import io.github.scalaquest.core.TestsUtils
import io.github.scalaquest.core.model.Direction
import org.scalatest.wordspec.AnyWordSpec

class SimpleRoomExtTest extends AnyWordSpec {
  import TestsUtils._
  import TestsUtils.model._

  "A Room" should {
    val room  = Room("room", Map(Direction.North -> targetRoom.ref), Set.empty)
    val state = roomsLens.modify(_ + (room.ref -> room))(simpleState)

    "return a Room if the direction has an associated Room" in {
      assert(
        room.neighbor(Direction.North)(state).contains(targetRoom),
        "The Room has not the correct room in the direction"
      )
    }

    "not return anything if the direction has not associated room" in {
      assert(
        room.neighbor(Direction.South)(state).isEmpty,
        "The Room returns another Room in an empty direction"
      )
    }
  }
}
