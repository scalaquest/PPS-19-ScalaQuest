package io.github.scalaquest.core.model.behaviorBased.commons.items.impl

import io.github.scalaquest.core.TestsUtils
import io.github.scalaquest.core.model.ItemDescription
import org.scalatest.wordspec.AnyWordSpec
import TestsUtils.model._

class FoodTest extends AnyWordSpec {

  "A Food is an item that" when {
    val food            = Food(ItemDescription("food"), Eatable.builder(), Seq(Takeable.builder()))
    val eatableBehavior = Eatable.builder()(food)

    "instantiated" should {
      "have the eatable item" in {
        assert(
          food.eatable == eatableBehavior,
          "behaviors are not correctly instantiated."
        )
      }
    }
  }
}
