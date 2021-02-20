package io.github.scalaquest.examples.escaperoom

import model.Messages._
import model._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class ItemsTest extends AnyWordSpec with Matchers {
  implicit val s: S = EscapeRoom.state

  "The Items object" should {
    "contains a red non-dangerous apple" in {
      Items.redApple.eatable.eat(EscapeRoom.state)._2 should not contain Lost
    }

    "contains a green dangerous apple" in {
      Items.greenApple.eatable.eat(EscapeRoom.state)._2 should contain(Lost)
    }

    "contains a pair of complementary hatches, between living room and basement" in {
      Items.hatch.roomLink.endRoom shouldBe House.livingRoom
      Items.basementHatch.roomLink.endRoom shouldBe House.basement
    }

    "contains a hatch key, that opens the hatch from the basement" in {
      Items.hatch.roomLink.openable.flatMap(_.requiredKey) shouldBe Some(Items.hatchKey)
    }

    "contains a coffer with the hatch key inside" in {
      Items.chest.container.items should contain(Items.hatchKey)
    }

    "contains a crowbar that opens the doorway" in {
      Items.doorway.behaviors
        .collectFirst({ case op: Openable => op.requiredKey })
        .flatten shouldBe Some(Items.crowbar)
    }

    "contains a set with all the items available" in {
      Items.allTheItems shouldBe Set(
        Items.redApple,
        Items.greenApple,
        Items.chest,
        Items.hatchKey,
        Items.hatch,
        Items.doorway,
        Items.crowbar,
        Items.basementHatch
      )
    }
  }
}
