package io.github.scalaquest.core.model.behaviorBased.commons.items.impl

import io.github.scalaquest.core.TestsUtils
import io.github.scalaquest.core.model.ItemDescription
import org.scalatest.wordspec.AnyWordSpec
import TestsUtils.model._

class GenericItemTest extends AnyWordSpec {

  "A GenericItem" when {
    val genericItem = GenericItem(
      ItemDescription("item"),
      Seq(Takeable.builder(), Eatable.builder())
    )

    val someBehaviors = Seq(Takeable.builder()(genericItem), Eatable.builder()(genericItem))

    "instantiated" should {
      "take whatever number of behaviors" in {
        assert(genericItem.behaviors == someBehaviors, "behaviors are not correctly instantiated.")
      }
    }
  }
}
