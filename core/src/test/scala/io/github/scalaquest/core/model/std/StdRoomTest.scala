package io.github.scalaquest.core.model.std

import org.scalatest.wordspec.AnyWordSpec
import io.github.scalaquest.core.TestsUtils.targetRoom
import io.github.scalaquest.core.model.Direction.Direction
import io.github.scalaquest.core.model.{Direction, Room}

class StdRoomTest extends AnyWordSpec {
  "A Room" when {

    val sRoom: Room = Room("startRoom", () => Map[Direction, Room](Direction.NORTH -> targetRoom))

    "someone accesses a neighbor" should {
      "return a Room if the direction has an associated Room" in {
        assert(
          sRoom.neighbors(Direction.NORTH).contains(targetRoom),
          "The Room has not the correct room in the direction"
        )
      }

      "not return anything if the direction has not associated room" in {
        assert(
          sRoom.neighbors(Direction.SOUTH).isEmpty,
          "The Room returns another Room in an empty direction"
        )
      }
    }
  }
}
