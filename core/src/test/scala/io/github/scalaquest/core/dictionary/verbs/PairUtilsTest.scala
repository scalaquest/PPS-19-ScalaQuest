/*
 * Copyright (c) 2021 ScalaQuest Team.
 *
 * This file is part of ScalaQuest, and is distributed under the terms of the MIT License as
 * described in the file LICENSE in the ScalaQuest distribution's top directory.
 */

package io.github.scalaquest.core.dictionary.verbs

import io.github.scalaquest.core.model.Action
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class PairUtilsTest extends AnyWordSpec with Matchers {

  case object TestAction extends Action
  "The binding" should {
    "have no preposition" in {
      val verb = Transitive("get", TestAction, None)
      verb.binding shouldBe (("get", None) -> TestAction)
    }
    "have a preposition" in {
      val verb = Transitive("pick", TestAction, Some("up"))
      verb.binding shouldBe (("pick", Some("up")) -> TestAction)
    }
  }
}
