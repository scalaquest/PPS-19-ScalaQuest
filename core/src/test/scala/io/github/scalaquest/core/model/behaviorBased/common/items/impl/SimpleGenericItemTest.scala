package io.github.scalaquest.core.model.behaviorBased.common.items.impl

import io.github.scalaquest.core.model.{ItemDescription, ItemRef}
import org.scalatest.wordspec.AnyWordSpec
import io.github.scalaquest.core.model.behaviorBased.impl.SimpleModel.{
  SimpleEatable,
  SimpleGenericItem,
  SimpleTakeable
}

class SimpleGenericItemTest extends AnyWordSpec {
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
