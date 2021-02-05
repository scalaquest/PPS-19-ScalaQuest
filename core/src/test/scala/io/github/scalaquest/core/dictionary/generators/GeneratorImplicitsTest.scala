package io.github.scalaquest.core.dictionary.generators

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class GeneratorImplicitsTest extends AnyWordSpec with Matchers {

  "Using the implicits" when {
    "calling a new generatorK from a list" should {
      "return an instance" in {
        import implicits.listGenerator
        import cats.Monoid
        implicit val generator = Generator.instance((x: Int) => x)
        implicit val monoid    = Monoid.instance[Int](0, _ + _)
        val generatorK         = GeneratorK[List, Int, Int]
        generatorK.generate(List(1, 2, 3)) shouldBe 6
      }
      "calling a new generatorK from a list to a map" should {
        "return an instance" in {
          import implicits.listToMapGenerator
          implicit val generator = Generator.makeEntry((x: Int) => x -> Math.pow(x, 2))
          val generatorK         = GeneratorK[List, Int, Map[Int, Double]]
          generatorK.generate(List(1, 2, 3)) shouldBe Map(
            1 -> 1,
            2 -> 4,
            3 -> 9
          )
        }
      }
    }
  }
}
