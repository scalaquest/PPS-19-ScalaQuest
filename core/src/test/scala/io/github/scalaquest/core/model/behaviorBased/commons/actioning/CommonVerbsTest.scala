package io.github.scalaquest.core.model.behaviorBased.commons.actioning

import io.github.scalaquest.core.dictionary.verbs.Verb
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class CommonVerbsTest extends AnyWordSpec with Matchers {
  "CommonVerbs" should {
    "Contain a set of commonly used verbs, with their name" in {
      CommonVerbs() shouldBe a[Set[Verb]]
    }
  }
}
