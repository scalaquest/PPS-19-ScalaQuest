package io.github.scalaquest.examples.escaperoom

import io.github.scalaquest.core.model.Direction
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class GeographyTests extends AnyWordSpec with Matchers {
  "The House object" should {
    "contains a basement with a coffer, a crowbar, a hatch" in {
      Geography.basement.items(App.state) shouldBe Set(Items.chest, Items.crowbar, Items.hatch)
    }

    "contains a basement not linked with other room" in {
      Geography.basement.neighbors(App.state) shouldBe Map.empty
    }

    "contains a living room with the two apples, the basement hatch, the doorway" in {
      Geography.livingRoom.items(App.state) shouldBe Set(
        Items.greenApple,
        Items.redApple,
        Items.basementHatch,
        Items.doorway
      )
    }

    "contains a living room linked to the North side with a bathroom" in {
      Geography.livingRoom.neighbors(App.state) shouldBe Map(
        Direction.North -> Geography.bathroom,
        Direction.Down  -> Geography.basement
      )
    }

    "contains an empty bathroom" in {
      Geography.bathroom.items(App.state) shouldBe Set.empty
    }

    "contains a bathroom linked on the South side with the living room" in {
      Geography.bathroom.neighbors(App.state) shouldBe Map(Direction.South -> Geography.livingRoom)
    }
  }
}
