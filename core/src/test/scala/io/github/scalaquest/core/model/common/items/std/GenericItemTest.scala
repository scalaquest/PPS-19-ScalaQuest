package io.github.scalaquest.core.model.common.items.std

import io.github.scalaquest.core.model.ItemRef
import org.scalatest.wordspec.AnyWordSpec
import io.github.scalaquest.core.model.std.StdModel.{Eatable, GenericItem, Takeable}

class GenericItemTest extends AnyWordSpec {
  "A GenericItem" when {
    val someBehaviors = Seq(Takeable(), Eatable())
    val genericItem   = GenericItem(new ItemRef {}, someBehaviors.head, someBehaviors(1))

    "instantiated" should {
      "take whatever number of behaviors" in {
        assert(genericItem.behaviors == someBehaviors, "behaviors are not correctly instantiated.")
      }
    }
  }
}
