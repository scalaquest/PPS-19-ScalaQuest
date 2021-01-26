package io.github.scalaquest.core.pipeline.resolver

import io.github.scalaquest.core.TestsUtils.{appleItemRef, doorItemRef, keyItemRef, simpleState}
import io.github.scalaquest.core.model.Action.Common.{Open, Take}
import io.github.scalaquest.core.model.BaseItem
import io.github.scalaquest.core.model.behaviorBased.impl.SimpleModel
import io.github.scalaquest.core.pipeline.parser.{AbstractSyntaxTree, SimpleParserResult}
import org.scalatest.wordspec.AnyWordSpec

class ResolverTest extends AnyWordSpec {
  "A Resolver" when {
    val model    = SimpleModel
    val resolver = Resolver.builder(model)(simpleState)

    "receives an Intransitive AST" should {
      val parserResult   = SimpleParserResult(AbstractSyntaxTree.Intransitive("open", "you"))
      val maybeStatement = resolver.resolve(parserResult).map(_.statement)

      "produce the right Intransitive Statement" in {
        for { left <- maybeStatement.left } yield fail(
          s"Resolver has not produced any statement: $left"
        )

        maybeStatement map {
          case Statement.Intransitive(action) if action == Open =>
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
          AbstractSyntaxTree.Transitive("take", "you", BaseItem("apple"))
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
          AbstractSyntaxTree.Ditransitive("open", "you", BaseItem("door"), BaseItem("key"))
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
  }
}
