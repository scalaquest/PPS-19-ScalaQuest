/*
 * Copyright (c) 2021 ScalaQuest Team.
 *
 * This file is part of ScalaQuest, and is distributed under the terms of the MIT License as
 * described in the file LICENSE in the ScalaQuest distribution's top directory.
 */

package io.github.scalaquest.core.dictionary.verbs

import io.github.scalaquest.core.model.Action
import io.github.scalaquest.core.parsing.scalog.{Atom, Compound, Fact, Number}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class ClauseOpsTest extends AnyWordSpec with Matchers {

  case object TestAction extends Action
  "The clause generation" when {
    "provided an intransitive verb" should {
      "generate a fact without a preposition" in {
        val verb = Intransitive("inspect", TestAction, None)
        verb.clause shouldBe Fact(Compound(Atom("verb"), Number(1), List(Atom("inspect"))))
      }
      "generate a fact with a preposition" in {
        val verb = Intransitive("look", TestAction, Some("around"))
        verb.clause shouldBe Fact(
          Compound(Atom("verb"), Number(1), List(Atom("look"), Atom("around")))
        )
      }
    }
    "provided a transitive verb" should {
      "generate a fact without a preposition" in {
        val verb = Transitive("get", TestAction, None)
        verb.clause shouldBe Fact(Compound(Atom("verb"), Number(2), List(Atom("get"))))
      }
      "generate a fact with a preposition" in {
        val verb = Transitive("pick", TestAction, Some("up"))
        verb.clause shouldBe Fact(Compound(Atom("verb"), Number(2), List(Atom("pick"), Atom("up"))))
      }
    }
    "provided a ditransitive verb" should {
      "generate a fact without a preposition" in {
        val verb = Ditransitive("give", TestAction, None)
        verb.clause shouldBe Fact(Compound(Atom("verb"), Number(3), List(Atom("give"))))
      }
      "generate a fact with a preposition" in {
        val verb = Ditransitive("open", TestAction, Some("with"))
        verb.clause shouldBe Fact(
          Compound(Atom("verb"), Number(3), List(Atom("open"), Atom("with")))
        )
      }
    }
  }
}
