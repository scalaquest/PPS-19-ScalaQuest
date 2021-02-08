package io.github.scalaquest.core.model.behaviorBased

import io.github.scalaquest.core.TestsUtils
import org.scalatest.wordspec.AnyWordSpec
import io.github.scalaquest.core.model.Direction

class SimpleRoomTest extends AnyWordSpec {
  import TestsUtils._

  "A Room" when {
    "someone accesses a neighbor" should {
      "return a Room if the direction has an associated Room" in {
        assert(
          startRoom.neighbor(Direction.North)(simpleState).contains(targetRoom),
          "The Room has not the correct room in the direction"
        )
      }

      "not return anything if the direction has not associated room" in {
        assert(
          startRoom.neighbor(Direction.South)(simpleState).isEmpty,
          "The Room returns another Room in an empty direction"
        )
      }
    }
  }
}
