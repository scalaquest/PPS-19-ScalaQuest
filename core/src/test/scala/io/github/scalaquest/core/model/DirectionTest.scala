package io.github.scalaquest.core.model

import io.github.scalaquest.core.model.Direction.{Down, East, North, South, Up, West}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class DirectionTest extends AnyWordSpec with Matchers {
  "The Direction companion object" should {
    "have a North direction correctly configured" in {
      Direction.North.toString shouldBe "North"
    }

    "have a South direction correctly configured" in {
      Direction.South.toString shouldBe "South"
    }

    "have a East direction correctly configured" in {
      Direction.East.toString shouldBe "East"
    }

    "have a West direction correctly configured" in {
      Direction.West.toString shouldBe "West"
    }

    "have a Up direction correctly configured" in {
      Direction.Up.toString shouldBe "Up"
    }

    "have a Down direction correctly configured" in {
      Direction.Down.toString shouldBe "Down"
    }

    "have a set that contains all the directions" in {
      Direction.all shouldBe Set(North, South, East, West, Up, Down)
    }
  }
}
