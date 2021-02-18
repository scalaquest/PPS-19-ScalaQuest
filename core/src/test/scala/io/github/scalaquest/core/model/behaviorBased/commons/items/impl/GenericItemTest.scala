package io.github.scalaquest.core.model.behaviorBased.commons.items.impl

import io.github.scalaquest.core.TestsUtils
import io.github.scalaquest.core.model.ItemDescription
import org.scalatest.wordspec.AnyWordSpec
import TestsUtils.model._
import org.scalatest.matchers.should.Matchers

class GenericItemTest extends AnyWordSpec with Matchers {

  "A GenericItem" should {
    val behBuilders     = Seq(Takeable.builder(), Eatable.builder())
    val genericItem     = GenericItem(ItemDescription("item"), behBuilders)
    val targetBehaviors = behBuilders.map(_(genericItem))

    "take a sequence of behaviorBuilders, memorized as behaviors" in {
      genericItem.behaviors shouldBe targetBehaviors
    }
  }
}
