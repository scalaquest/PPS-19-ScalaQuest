package io.github.scalaquest.examples.escaperoom

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import model._

class ItemsTest extends AnyWordSpec with Matchers {

  "The Items object" should {
    "contains a red non-dangerous apple" in {
      val newState = Items.redApple.foodBehavior.eat(Items.redApple)(EscapeRoom.state)
      assert(!newState.messages.contains(Lost))
    }

    "contains a green dangerous apple" in {
      val newState = Items.greenApple.foodBehavior.eat(Items.greenApple)(EscapeRoom.state)
      assert(newState.messages.contains(Lost))
    }

    "contains a pair of complementary hatches, between living room and basement" in {
      Items.hatch.doorBehavior.endRoom shouldBe House.livingRoom
      Items.basementHatch.doorBehavior.endRoom shouldBe House.basement
    }

    "contains a hatch key, that opens the hatch from the basement" in {
      Items.hatch.doorBehavior.openable
        .collectFirst({ op: Openable => op.requiredKey })
        .flatten shouldBe Some(Items.hatchKey)
    }

    "contains a coffer with the hatch key inside" in {
      val newState = Items.coffer.behaviors
        .collectFirst({ case op: Openable => op.open(Items.coffer) })
        .fold(EscapeRoom.state)(r => r(EscapeRoom.state))

      assert(newState.location.items(newState).contains(Items.hatchKey))
    }

    "contains a crowbar that opens the doorway" in {
      Items.doorway.behaviors
        .collectFirst({ case op: Openable => op.requiredKey })
        .flatten shouldBe Some(Items.crowbar)
    }
  }
}
