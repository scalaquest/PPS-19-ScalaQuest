package io.github.scalaquest.core.model.std

import io.github.scalaquest.core.model.common.behaviors.std.BehaviorsTestsUtils.startRoom
import org.scalatest.wordspec.AnyWordSpec
import io.github.scalaquest.core.model.std.StdModel.{StdGameState, StdPlayer, StdState}

class StdStateTest extends AnyWordSpec {
  "A StdState" must {
    "be instantiated with a StdGameState, a StdPlayer and a set of messages" in {
      assertCompiles("val state: StdState = StdState(StdGameState(" +
        "StdPlayer(Set(), location = startRoom), false, Set(startRoom), Map(startRoom -> Set())), Seq())")
    }
  }
}
