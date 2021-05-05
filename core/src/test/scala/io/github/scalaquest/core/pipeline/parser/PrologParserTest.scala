/*
 * Copyright (c) 2021 ScalaQuest Team.
 *
 * This file is part of ScalaQuest, and is distributed under the terms of the MIT License as
 * described in the file LICENSE in the ScalaQuest distribution's top directory.
 */

package io.github.scalaquest.core.pipeline.parser

import io.github.scalaquest.core.model.{BaseItem, DecoratedItem}
import io.github.scalaquest.core.parsing.engine.{DCGLibrary, Engine, Theory}
import io.github.scalaquest.core.pipeline.lexer.SimpleLexerResult
import org.scalatest.wordspec.AnyWordSpec

import scala.io.Source

class PrologParserTest extends AnyWordSpec {

  val source = Source.fromResource("base.pl").mkString +
    """
      |
      |name(key).
      |name(door).
      |name(apple).
      |name(bag).
      |
      |adjective(little).
      |adjective(red).
      |adjective(golden).
      |adjective(shiny).
      |
      |verb(1, inspect).
      |verb(2, take).
      |verb(2, pick, up).
      |verb(3, open, with).
      |verb(3, put, into).
      |verb(3, put, in).
      |
      |""".stripMargin
  val parser: Parser = Parser(Engine(Theory(source), Set(DCGLibrary)))

  "A parser" when {
    "provided an empty sequence of tokens" should {
      "return an empty value" in {
        assert(parser.parse(SimpleLexerResult(Seq())).isEmpty)
      }
    }
    "provided an intransitive sentence" should {
      "return an intransitive ast" in {
        assert(
          parser
            .parse(SimpleLexerResult(Seq("inspect")))
            .map(_.tree)
            .contains(AbstractSyntaxTree.Intransitive("inspect", None, "you"))
        )
      }
    }
    "provided a transitive sentence" should {
      "return a transitive ast" in {
        assert(
          parser
            .parse(SimpleLexerResult(Seq("take", "the", "key")))
            .map(_.tree)
            .contains(AbstractSyntaxTree.Transitive("take", None, "you", BaseItem("key")))
        )
      }
      "recognize phrasal verbs in the right form" in {
        assert(
          parser
            .parse(SimpleLexerResult(Seq("pick", "the", "key", "up")))
            .map(_.tree)
            .contains(AbstractSyntaxTree.Transitive("pick", Some("up"), "you", BaseItem("key")))
        )
      }
      "recognize phrasal verbs the wrong form" in {
        assert(
          parser
            .parse(SimpleLexerResult(Seq("pick", "up", "the", "key")))
            .map(_.tree)
            .contains(AbstractSyntaxTree.Transitive("pick", Some("up"), "you", BaseItem("key")))
        )
      }
    }
    "provided an decorated item" should {
      "wrap it in a decorated item" in {
        val decoratedItem = DecoratedItem("little", BaseItem("key"))
        assert(
          parser
            .parse(SimpleLexerResult(Seq("take", "the", "little", "key")))
            .map(_.tree)
            .contains(AbstractSyntaxTree.Transitive("take", None, "you", decoratedItem))
        )
      }
      "wrap it in a nested decorated item" in {
        val decoratedItem =
          DecoratedItem("little", DecoratedItem("golden", DecoratedItem("shiny", BaseItem("key"))))
        assert(
          parser
            .parse(SimpleLexerResult(Seq("take", "the", "little", "golden", "shiny", "key")))
            .map(_.tree)
            .contains(AbstractSyntaxTree.Transitive("take", None, "you", decoratedItem))
        )
      }
    }
    "provided a ditransitive sentence" should {
      "return a ditransitive ast" in {
        assert(
          parser
            .parse(SimpleLexerResult(Seq("open", "the", "door", "with", "the", "key")))
            .map(_.tree)
            .contains(
              AbstractSyntaxTree
                .Ditransitive("open", Some("with"), "you", BaseItem("door"), BaseItem("key"))
            )
        )
      }
      "return empty if the preposition is in the wrong position" in {
        assert(
          parser
            .parse(SimpleLexerResult(Seq("open", "the", "door", "the", "key", "with")))
            .isEmpty
        )
      }
      "distinguish between direct and indirect objects" in {
        assert(
          parser
            .parse(SimpleLexerResult(Seq("put", "the", "apple", "in", "the", "bag")))
            .map(_.tree)
            .contains(
              AbstractSyntaxTree
                .Ditransitive("put", Some("in"), "you", BaseItem("apple"), BaseItem("bag"))
            )
        )
      }
    }
  }
}
