package io.github.scalaquest.core.model.behaviorBased.commons.items.impl

import io.github.scalaquest.core.TestsUtils
import io.github.scalaquest.core.model.ItemDescription
import org.scalatest.wordspec.AnyWordSpec
import TestsUtils._
import TestsUtils.model._
import org.scalatest.matchers.should.Matchers

class KeyTest extends AnyWordSpec with Matchers {
  "A Key" when {
    val key: Key = Key(
      ItemDescription("key"),
      extraBehavBuilders = Seq(Takeable.builder(), Eatable.builder())
    )

    "associated to an Openable behavior" should {
      val openable = Openable.lockedBuilder(requiredKey = key)(apple)

      "be used as the key for the opening" in {
        openable.requiredKey should contain(key)
      }
    }
  }
}
