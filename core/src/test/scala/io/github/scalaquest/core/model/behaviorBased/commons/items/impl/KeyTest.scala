package io.github.scalaquest.core.model.behaviorBased.commons.items.impl

import io.github.scalaquest.core.TestsUtils
import io.github.scalaquest.core.model.ItemDescription
import org.scalatest.wordspec.AnyWordSpec

class KeyTest extends AnyWordSpec {
  import TestsUtils._
  import TestsUtils.model._

  "A Key" when {
    val key       = Key(ItemDescription("key"), Seq(Takeable.builder(), Eatable.builder()))
    val behaviors = Seq(Takeable.builder()(key), Eatable.builder()(key))

    "instantiated" should {
      "take whatever number of behaviors" in {
        assert(key.behaviors == behaviors, "behaviors are not correctly instantiated.")
      }
    }

    "associated to an Openable behavior" should {
      val openable = Openable.builder(requiredKey = Some(key))(apple)

      "be used as the key for the opening" in {
        assert(openable.requiredKey.contains(key), "The key could not be used for opening")
      }
    }
  }
}
