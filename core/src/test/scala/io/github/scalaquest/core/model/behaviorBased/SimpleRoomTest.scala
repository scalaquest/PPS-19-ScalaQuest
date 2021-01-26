package io.github.scalaquest.core.model.behaviorBased

import org.scalatest.wordspec.AnyWordSpec
import io.github.scalaquest.core.TestsUtils.targetRoom
import io.github.scalaquest.core.model.Direction
import io.github.scalaquest.core.model.behaviorBased.impl.SimpleModel.Room

class SimpleRoomTest extends AnyWordSpec {
  "A Room" when {
    val room = Room("room", Map(Direction.North -> targetRoom.ref), Set())

    "someone accesses a neighbor" should {
      "return a Room if the direction has an associated Room" in {
        assert(
          room.neighbor(Direction.North).contains(targetRoom.ref),
          "The Room has not the correct room in the direction"
        )
      }

      "not return anything if the direction has not associated room" in {
        assert(
          room.neighbor(Direction.South).isEmpty,
          "The Room returns another Room in an empty direction"
        )
      }
    }
  }
}
