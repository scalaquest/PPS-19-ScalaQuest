package io.github.scalaquest.core.model.behaviorBased.common.items.impl

import io.github.scalaquest.core.model.ItemRef
import org.scalatest.wordspec.AnyWordSpec
import io.github.scalaquest.core.model.behaviorBased.impl.SimpleModel.{SimpleEatable, SimpleFood}
import io.github.scalaquest.core.pipeline.parser.ItemDescription

class SimpleFoodTest extends AnyWordSpec {
  "A Food is an item that" when {
    val eatableBehaviour = SimpleEatable()
    val food             = SimpleFood(ItemDescription("food"), new ItemRef {}, eatableBehaviour)

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
