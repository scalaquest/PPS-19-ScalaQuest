package io.github.scalaquest.core.model.behaviorBased.commons.items.impl

import io.github.scalaquest.core.TestsUtils
import io.github.scalaquest.core.model.ItemDescription
import org.scalatest.wordspec.AnyWordSpec

class FoodTest extends AnyWordSpec {
  import TestsUtils.model._

  "A Food is an item that" when {
    val eatableBehavior = Eatable()
    val food            = Food(ItemDescription("food"), eatableBehavior, Seq(Takeable()))

    "instantiated" should {
      "have the eatable item" in {
        assert(
          food.foodBehavior == eatableBehavior,
          "behaviors are not correctly instantiated."
        )
      }
    }
  }
}
