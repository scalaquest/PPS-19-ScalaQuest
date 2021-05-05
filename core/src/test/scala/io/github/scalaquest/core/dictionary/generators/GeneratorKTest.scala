/*
 * Copyright (c) 2021 ScalaQuest Team.
 *
 * This file is part of ScalaQuest, and is distributed under the terms of the MIT License as
 * described in the file LICENSE in the ScalaQuest distribution's top directory.
 */

package io.github.scalaquest.core.dictionary.generators

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class GeneratorKTest extends AnyWordSpec with Matchers {

  "A GeneratorK" should {
    implicit val generator = Generator.makeEntry((x: Int) => x -> Math.pow(x, 2))
    "generate a map of entries" in {
      val generatorK = new GeneratorK[List, Int, Map[Int, Double]]
      generatorK.generate(List(1, 2, 3)) shouldBe Map(
        1 -> 1,
        2 -> 4,
        3 -> 9
      )
    }
    "generate the previous natural number" in {
      implicit val generator = Generator.instance((x: Int) => if (x > 1) Some(x - 1) else None)
      val generatorK         = new GeneratorK[Option, Int, Option[Int]]
      generatorK.generate(Some(5)) shouldBe Some(4)
      generatorK.generate(None) shouldBe None
    }
  }
}
