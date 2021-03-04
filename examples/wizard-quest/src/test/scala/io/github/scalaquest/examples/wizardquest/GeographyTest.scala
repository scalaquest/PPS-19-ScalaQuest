package io.github.scalaquest.examples.wizardquest

import io.github.scalaquest.core.model.Direction
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class GeographyTest extends AnyWordSpec with Matchers {
  implicit val state: S = App.state

  "The chamber of Secrets" should {
    "contains a basilisk, Tom Riddle, the Tom diary, a sorting hat and Ginny Weasley" in {
      Geography.chamberOfSecrets.items shouldBe Set(
        Items.basilisk,
        Items.ginny,
        Items.sortingHat,
        Items.tomDiary,
        Items.tom
      )
    }
    "be the start room" in {
      App.state.location shouldBe Geography.chamberOfSecrets
    }

    "have tunnel room in East direction" in {
      Geography.chamberOfSecrets.neighbor(Direction.East) shouldBe Some(Geography.tunnel)
    }
  }

  "The tunnel" should {
    "contain a stone" in {
      Geography.tunnel.items should contain(Items.stone)
    }
  }

}
