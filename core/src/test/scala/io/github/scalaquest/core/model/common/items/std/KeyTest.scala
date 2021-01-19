package io.github.scalaquest.core.model.common.items.std

import io.github.scalaquest.core.model.ItemRef
import org.scalatest.wordspec.AnyWordSpec
import io.github.scalaquest.core.model.std.StdModel.{Eatable, GenericItem, Key, Openable, Takeable}

class KeyTest extends AnyWordSpec {
  "A Key" when {
    val behaviors = Seq(Takeable(), Eatable())
    val key       = Key(new ItemRef {}, behaviors.head, behaviors(1))

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
