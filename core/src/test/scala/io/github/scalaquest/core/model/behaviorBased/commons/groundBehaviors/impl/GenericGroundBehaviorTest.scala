package io.github.scalaquest.core.model.behaviorBased.commons.groundBehaviors.impl

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import io.github.scalaquest.core.TestsUtils.model.GenericGroundBehavior

class GenericGroundBehaviorTest extends AnyWordSpec with Matchers {

  "A GenericGroundBehavior" when {
    "is instantiated without any trigger" should {
      val genericGround = GenericGroundBehavior()
      "have empty triggers" in {
        genericGround.triggers shouldBe PartialFunction.empty
      }
    }
  }
}
