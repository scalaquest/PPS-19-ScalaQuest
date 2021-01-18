package io.github.scalaquest.core.model.common.items.std

import org.scalatest.wordspec.AnyWordSpec
import io.github.scalaquest.core.model.std.StdModel.{Eatable, Food}

class FoodTest extends AnyWordSpec {
  "A Food is an item that" when {
    val eatableBehaviour = Eatable()
    val food             = Food("apple", eatableBehaviour)

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
