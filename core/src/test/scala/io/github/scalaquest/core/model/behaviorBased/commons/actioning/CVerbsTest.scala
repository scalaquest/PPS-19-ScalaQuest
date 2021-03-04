package io.github.scalaquest.core.model.behaviorBased.commons.actioning

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class CVerbsTest extends AnyWordSpec with Matchers {
  "CommonVerbs" should {
    "Contain a set of commonly used verbs, with their name" in {
      CVerbs() shouldBe a[Set[_]]
    }
  }
}
