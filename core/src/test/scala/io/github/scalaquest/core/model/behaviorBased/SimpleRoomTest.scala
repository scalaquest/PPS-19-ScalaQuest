package io.github.scalaquest.core.model.behaviorBased

import org.scalatest.wordspec.AnyWordSpec
import io.github.scalaquest.core.TestsUtils.targetRoom
import io.github.scalaquest.core.model.Room
import io.github.scalaquest.core.model.Room.Direction

class SimpleRoomTest extends AnyWordSpec {
  "A Room" when {

    val sRoom: Room = Room("startRoom", Map[Direction, Room](Direction.North -> targetRoom))

    "someone accesses a neighbor" should {
      "return a Room if the direction has an associated Room" in {
        assert(
          sRoom.neighbors(Direction.North).contains(targetRoom),
          "The Room has not the correct room in the direction"
        )
      }

      "not return anything if the direction has not associated room" in {
        assert(
          sRoom.neighbors(Direction.South).isEmpty,
          "The Room returns another Room in an empty direction"
        )
      }
    }
  }
}
