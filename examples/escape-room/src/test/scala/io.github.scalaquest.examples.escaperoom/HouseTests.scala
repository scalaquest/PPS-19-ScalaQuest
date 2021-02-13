package io.github.scalaquest.examples.escaperoom

import io.github.scalaquest.core.model.Direction
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class HouseTests extends AnyWordSpec with Matchers {
  "The House object" should {
    "contains a basement with a coffer, a crowbar, a hatch" in {
      House.basement.items(EscapeRoom.state) shouldBe Set(Items.coffer, Items.crowbar, Items.hatch)
    }

    "contains a basement not linked with other room" in {
      House.basement.neighbors(EscapeRoom.state) shouldBe Map.empty
    }

    "contains a living room with the two apples, the basement hatch, the doorway" in {
      House.livingRoom.items(EscapeRoom.state) shouldBe Set(
        Items.greenApple,
        Items.redApple,
        Items.basementHatch,
        Items.doorway
      )
    }

    "contains a living room linked to the North side with a bathroom" in {
      House.livingRoom.neighbors(EscapeRoom.state) shouldBe Map(
        Direction.North -> House.bathroom,
        Direction.Down  -> House.basement
      )
    }

    "contains an empty bathroom" in {
      House.bathroom.items(EscapeRoom.state) shouldBe Set.empty
    }

    "contains a bathroom linked on the South side with the living room" in {
      House.bathroom.neighbors(EscapeRoom.state) shouldBe Map(Direction.South -> House.livingRoom)
    }
  }
}
