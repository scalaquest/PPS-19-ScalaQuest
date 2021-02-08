package io.github.scalaquest.core.model.behaviorBased.commons.items.impl

import io.github.scalaquest.core.TestsUtils
import io.github.scalaquest.core.model.ItemDescription
import org.scalatest.wordspec.AnyWordSpec

class KeyTest extends AnyWordSpec {
  import TestsUtils.model._

  "A Key" when {
    val behaviors = Seq(Takeable(), Eatable())
    val key       = Key(ItemDescription("key"), Seq(behaviors.head, behaviors(1)))

    "instantiated" should {
      "take whatever number of behaviors" in {
        assert(key.behaviors == behaviors, "behaviors are not correctly instantiated.")
      }
    }

    "associated to an Openable behavior" should {
      val openable = Openable(requiredKey = Some(key))

      "be used as the key for the opening" in {
        assert(openable.requiredKey.contains(key), "The key could not be used for opening")
      }
    }
  }
}
