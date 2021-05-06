/*
 * Copyright (c) 2021 ScalaQuest Team.
 *
 * This file is part of ScalaQuest, and is distributed under the terms of the MIT License as
 * described in the file LICENSE in the ScalaQuest distribution's top directory.
 */

package io.github.scalaquest.core.pipeline.resolver

import io.github.scalaquest.core.TestsUtils.model._
import io.github.scalaquest.core.TestsUtils.{appleItemRef, doorItemRef, keyItemRef, simpleState}
import io.github.scalaquest.core.model.ItemDescription.dsl.{d, i}
import io.github.scalaquest.core.model.behaviorBased.commons.actioning.CActions.{Go, Open, Take}
import io.github.scalaquest.core.model.behaviorBased.simple.SimpleModel
import io.github.scalaquest.core.model.{BaseItem, Direction, ItemDescription}
import io.github.scalaquest.core.pipeline.parser.{AbstractSyntaxTree, SimpleParserResult}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class ResolverTest extends AnyWordSpec with Matchers {
  "A Resolver" when {
    val model = SimpleModel
    val greenApple: I = GenericItem(
      ItemDescription("apple", "green"),
      Seq(Takeable.builder(), Eatable.builder())
    )
    val newState = simpleState.copyWithItemInLocation(greenApple)
    val resolver = Resolver.builder(model)(newState)

    "receives an Intransitive AST" should {
      val parserResult =
        SimpleParserResult(AbstractSyntaxTree.Intransitive("go north", None, "you"))
      val maybeStatement = resolver.resolve(parserResult).map(_.statement)

      "produce the right Intransitive Statement" in {
        for { left <- maybeStatement.left } yield fail(
          s"Resolver has not produced any statement: $left"
        )

        maybeStatement map {
          case Statement.Intransitive(action) if action == Go(Direction.North) =>
            succeed
          case Statement.Intransitive(_) =>
            fail("Resolver has produced a wrong Intransitive Statement")
          case _ =>
            fail("Resolver has not produced an Intransitive Statement")
        }
      }
    }

    "receives an Transitive AST" should {
      val parserResult =
        SimpleParserResult(
          AbstractSyntaxTree.Transitive("take", None, "you", i(d("red"), "apple"))
        )
      val maybeStatement = resolver.resolve(parserResult).map(_.statement)

      "produce the right Transitive Statement" in {
        for { _ <- maybeStatement.left } yield fail("Resolver has not produced any statement")

        maybeStatement map {
          case Statement.Transitive(action, obj) if action == Take && obj == appleItemRef =>
            succeed
          case Statement.Transitive(_, _) =>
            fail("Resolver has produced a wrong Transitive Statement")
          case _ =>
            fail("Resolver has not produced a Transitive Statement")
        }
      }
    }

    "receives a Ditransitive AST" should {
      val parserResult =
        SimpleParserResult(
          AbstractSyntaxTree.Ditransitive(
            "open",
            Some("with"),
            "you",
            BaseItem("door"),
            BaseItem("key")
          )
        )
      val maybeStatement = resolver.resolve(parserResult).map(_.statement)

      "produce the right Ditransitive Statement" in {
        for { _ <- maybeStatement.left } yield fail("Resolver has not produced any statement")

        maybeStatement map {
          case Statement.Ditransitive(action, dirObj, sideObj)
              if action == Open && dirObj == doorItemRef && sideObj == keyItemRef =>
            succeed
          case Statement.Ditransitive(_, _, _) =>
            fail("Resolver has produced a wrong Ditransitive Statement")
          case _ =>
            fail("Resolver has not produced a Ditransitive Statement")
        }
      }
    }

    "receives an invalid AST" should {
      "produce a Left with a description of what went wrong" in {
        val parserResult   = SimpleParserResult(null)
        val maybeStatement = resolver.resolve(parserResult).map(_.statement)

        maybeStatement.fold(
          left => assert(left == "The statement is wrong.", "The left description is invalid."),
          _ => fail("Parser result is non empty, but it should be")
        )
      }
    }

    "receives a non resolvible Statement due to a name incomprension" should {
      "return a 'Which' message" in {
        val parserResult =
          SimpleParserResult(
            AbstractSyntaxTree.Transitive("take", None, "you", i("apple"))
          )

        resolver
          .resolve(parserResult)
          .map(_.statement)
          .left
          .foreach(_ should include("Which"))
      }
    }

    "receives a non resolvible Statement due to the object is not present in the room or bag" should {
      "not recognize the object" in {
        val parserResult =
          SimpleParserResult(
            AbstractSyntaxTree.Transitive("take", None, "you", i("banana"))
          )
        resolver
          .resolve(parserResult)
          .map(_.statement)
          .left
          .foreach(_ should include("can't see"))
      }
    }
  }
}
