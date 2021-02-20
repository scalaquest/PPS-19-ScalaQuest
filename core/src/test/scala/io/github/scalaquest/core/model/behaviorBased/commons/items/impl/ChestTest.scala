package io.github.scalaquest.core.model.behaviorBased.commons.items.impl

import io.github.scalaquest.core.TestsUtils
import io.github.scalaquest.core.TestsUtils.apple
import io.github.scalaquest.core.TestsUtils.model._
import io.github.scalaquest.core.model.ItemDescription.dsl.i
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class ChestTest extends AnyWordSpec with Matchers {

  "A unlocked Chest" should {
    val unlockedChest = Chest.createUnlocked(i("unlockedChest"), Set(apple))

    "have a Container behavior accessible from the interface" in {
      unlockedChest.container shouldBe a[Container]
    }

    "have a open state, initially closed accessible from the interface" in {
      unlockedChest.isOpen shouldBe false
    }

  }

  "A locked Chest" should {
    val lockedChest = Chest.createLocked(i("lockedChest"), Set(apple), TestsUtils.key)

    "need a key to be opened" in {
      lockedChest.container.openable.canBeOpened(Some(TestsUtils.key))(
        TestsUtils.simpleState
      ) shouldBe true
    }
  }
}
