/*
 * Copyright (c) 2021 ScalaQuest Team.
 *
 * This file is part of ScalaQuest, and is distributed under the terms of the MIT License as
 * described in the file LICENSE in the ScalaQuest distribution's top directory.
 */

package io.github.scalaquest.core.dictionary

import io.github.scalaquest.core.dictionary.generators.{Generator, combineAll}
import io.github.scalaquest.core.dictionary.verbs.{Transitive, Verb, VerbPrep}
import io.github.scalaquest.core.model.behaviorBased.simple.SimpleModel
import io.github.scalaquest.core.model.{Action, ItemDescription, ItemRef}
import io.github.scalaquest.core.parsing.scalog._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class DictionaryImplicitsTest extends AnyWordSpec with Matchers {

  val itemDescription = ItemDescription("item", "big", "red")

  val item = SimpleModel.SimpleGenericItem(
    itemDescription,
    ItemRef(itemDescription)
  )

  case object testAction extends Action
  val verb = Transitive("eat", testAction)

  "Implicits" should {
    import implicits._
    "provide a generator from verb to entry" in {
      val generator = Generator[Verb, Map[VerbPrep, Action]]
      generator.generate(verb) shouldBe Map(
        verb.binding
      )
    }
    "provide a generator from item to entry" in {
      val generator = Generator[Item, Map[ItemRef, Item]]
      generator.generate(item) shouldBe Map(
        item.ref -> item
      )
    }
    "provide a generator from verb to Program" in {
      val generator = Generator[Verb, Program]
      generator.generate(verb) shouldBe Set(
        Fact(Compound(Atom("verb"), Number(2), List(Atom("eat"))))
      )
    }
    "provide a generator from item to Program" in {
      val generator = Generator[Item, Program]
      generator.generate(item) shouldBe Set(
        Fact(Compound(Atom("name"), Atom("item"))),
        Fact(Compound(Atom("adjective"), Atom("big"))),
        Fact(Compound(Atom("adjective"), Atom("red")))
      )
    }
    "provide a Program Monoid instance" in {
      val program1: Program = Set(
        Fact(Compound(Atom("name"), Atom("item")))
      )
      val program2: Program = Set(
        Fact(Compound(Atom("adjective"), Atom("red")))
      )
      combineAll(program1, program2)(implicits.programMonoid) shouldBe Set(
        Fact(Compound(Atom("name"), Atom("item"))),
        Fact(Compound(Atom("adjective"), Atom("red")))
      )
    }
  }

}
