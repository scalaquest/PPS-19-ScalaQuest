package io.github.scalaquest.core.dictionary.generators

import io.github.scalaquest.core.dictionary.generators.Generator.makeEntry
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class GeneratorTest extends AnyWordSpec with Matchers {

  "The generator companion object" should {
    "create a generator of singlets" in {
      val instance = Generator.instance((x: Int) => Set(x * 2))
      instance.generate(10) shouldBe Set(20)
    }
    "create a map entry" in {
      val entry = makeEntry((x: Int) => x -> Math.pow(x, 2))
      entry.generate(5) shouldBe Map(5 -> 25)
    }
  }
}
