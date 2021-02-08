package io.github.scalaquest.core.model.behaviorBased.commons.items.impl

import io.github.scalaquest.core.model.{ItemDescription, ItemRef}
import org.scalatest.wordspec.AnyWordSpec
import io.github.scalaquest.core.model.behaviorBased.simple.SimpleModel.{
  SimpleEatable,
  SimpleKey,
  SimpleOpenable,
  SimpleTakeable
}

class KeyTest extends AnyWordSpec {
  "A Key" when {
    val behaviors = Seq(SimpleTakeable(), SimpleEatable())
    val key       = SimpleKey(ItemDescription("key"), new ItemRef {}, behaviors.head, behaviors(1))

    "instantiated" should {
      "take whatever number of behaviors" in {
        assert(key.behaviors == behaviors, "behaviors are not correctly instantiated.")
      }
    }

    "associated to an Openable behavior" should {
      val openable = SimpleOpenable(requiredKey = Some(key))

      "be used as the key for the opening" in {
        assert(openable.requiredKey.contains(key), "The key could not be used for opening")
      }
    }
  }
}
