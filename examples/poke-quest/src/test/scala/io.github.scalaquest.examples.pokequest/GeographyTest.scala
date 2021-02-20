package io.github.scalaquest.examples.pokequest

import io.github.scalaquest.core.model.Direction
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class GeographyTest extends AnyWordSpec with Matchers {
  implicit val state: S = PokeQuest.state

  "Vermilion City" should {
    "contain a sleeping Snorlax" in {
      Geography.vermillionCity.items should contain(Items.snorlax)
    }

    "be the start room" in {
      PokeQuest.state.location should be(Geography.vermillionCity)
    }
  }

  "The forest" should {
    "contain a Charizard" in {
      Geography.forest.items should contain(Items.charizard)
    }

    "be linked to the South to Vermillion City" in {
      Geography.forest.neighbor(Direction.South) should contain(Geography.vermillionCity)
    }
  }
}
