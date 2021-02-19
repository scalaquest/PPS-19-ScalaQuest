package io.github.scalaquest.core.model.behaviorBased.commons.items.impl

import io.github.scalaquest.core.TestsUtils.apple
import io.github.scalaquest.core.TestsUtils.model._
import io.github.scalaquest.core.model.ItemDescription.dsl.i
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class ChestTest extends AnyWordSpec with Matchers {

  "A Chest" should {
    val chest = Chest.createUnlocked(i("chest"), Set(apple))

    "have a Container behavrior accessible from the interface" in {
      chest.container shouldBe a[Container]
    }

    "have a open state, initially closed accessible from the interface" in {
      chest.isOpen shouldBe false
    }
  }
}
