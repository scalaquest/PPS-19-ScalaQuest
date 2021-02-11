package io.github.scalaquest.core.model.behaviorBased.commons.items.impl

import io.github.scalaquest.core.model.{ItemDescription, ItemRef}
import org.scalatest.wordspec.AnyWordSpec
import io.github.scalaquest.core.model.behaviorBased.simple.SimpleModel.{
  SimpleEatable,
  SimpleGenericItem,
  SimpleTakeable
}

class GenericItemTest extends AnyWordSpec {
  "A GenericItem" when {
    val someBehaviors = Seq(SimpleTakeable(), SimpleEatable())
    val genericItem = SimpleGenericItem(
      ItemDescription("item"),
      new ItemRef {},
      someBehaviors.head,
      someBehaviors(1)
    )

    "instantiated" should {
      "take whatever number of behaviors" in {
        assert(genericItem.behaviors == someBehaviors, "behaviors are not correctly instantiated.")
      }
    }
  }
}
