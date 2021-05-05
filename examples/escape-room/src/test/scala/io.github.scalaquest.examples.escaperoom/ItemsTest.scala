/*
 * Copyright (c) 2021 ScalaQuest Team.
 *
 * This file is part of ScalaQuest, and is distributed under the terms of the MIT License as
 * described in the file LICENSE in the ScalaQuest distribution's top directory.
 */

package io.github.scalaquest.examples.escaperoom

import model.CMessages._
import model._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class ItemsTest extends AnyWordSpec with Matchers {
  implicit val s: S = App.state

  "The Items object" should {
    "contains a red non-dangerous apple" in {
      Items.redApple.eatable.eat(App.state)._2 should not contain Lost
    }

    "contains a green dangerous apple" in {
      Items.greenApple.eatable.eat(App.state)._2 should contain(Lost)
    }

    "contains a pair of complementary hatches, between living room and basement" in {
      Items.hatch.roomLink.endRoom shouldBe Geography.livingRoom
      Items.basementHatch.roomLink.endRoom shouldBe Geography.basement
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
