package io.github.scalaquest.core.model.behaviorBased.commons.items.impl

import io.github.scalaquest.core.TestsUtils.model._
import io.github.scalaquest.core.model.ItemDescription
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class GenericItemTest extends AnyWordSpec with Matchers {

  "A GenericItem" should {
    val behBuilders     = Seq(Takeable.builder(), Eatable.builder())
    val genericItem     = GenericItem(ItemDescription("item"), behBuilders)
    val targetBehaviors = behBuilders.map(_(genericItem))

    "take a sequence of behaviorBuilders, memorized as behaviors" in {
      genericItem.behaviors shouldBe targetBehaviors
    }
  }

  "A GenericItem empty" should {
    val genericItem = GenericItem.empty(ItemDescription("item"))

    "not take any behaviors" in {
      genericItem.behaviors.size shouldBe 0
    }
  }

  "A GenericItem with single behavior" should {
    val genericItem = GenericItem.withSingleBehavior(ItemDescription("item"), Eatable.builder())

    "take a single behaviors" in {
      genericItem.behaviors.size shouldBe 1
    }
  }

  "A GenericItem with generics behavior" should {
    val genericItem = GenericItem.withGenBehavior(
      ItemDescription("item"),
      { case (_, _, _) => CReactions.finishGame(true) }
    )

    "take a sequence of behaviorBuilders, memorized as behaviors" in {
      genericItem.behaviors.size shouldBe 1
    }
  }

}
