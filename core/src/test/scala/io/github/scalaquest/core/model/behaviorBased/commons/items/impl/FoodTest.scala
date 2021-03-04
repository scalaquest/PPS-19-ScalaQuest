package io.github.scalaquest.core.model.behaviorBased.commons.items.impl

import io.github.scalaquest.core.TestsUtils
import io.github.scalaquest.core.model.ItemDescription
import org.scalatest.wordspec.AnyWordSpec
import TestsUtils.model._
import org.scalatest.matchers.should.Matchers

class FoodTest extends AnyWordSpec with Matchers {

  "A Food" should {
    val food = Food(ItemDescription("food"))

    "have an eatable behavior" in {
      food.eatable shouldBe a[Eatable]
    }
  }
}
