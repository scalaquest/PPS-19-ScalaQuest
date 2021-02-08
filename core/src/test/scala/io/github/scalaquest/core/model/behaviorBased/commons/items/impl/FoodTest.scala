package io.github.scalaquest.core.model.behaviorBased.commons.items.impl

import io.github.scalaquest.core.TestsUtils
import io.github.scalaquest.core.model.ItemDescription
import org.scalatest.wordspec.AnyWordSpec

class FoodTest extends AnyWordSpec {
  import TestsUtils.model._

  "A Food is an item that" when {
    val eatableBehaviour = Eatable()
    val food             = Food(ItemDescription("food"), eatableBehaviour)

    "instantiated" should {
      "have the eatable item" in {
        assert(
          food.foodBehavior == eatableBehaviour,
          "behaviors are not correctly instantiated."
        )
      }
    }
  }
}
