package io.github.scalaquest.core.pipeline.resolver

import io.github.scalaquest.core.model.Action
import io.github.scalaquest.core.model.common.Actions
import io.github.scalaquest.core.pipeline.interpreter.ItemRef
import io.github.scalaquest.core.pipeline.parser.{AST, ParserResult}
import org.scalatest.wordspec.AnyWordSpec

class ResolverTest extends AnyWordSpec {
  "A Resolver" when {
    "receives a ParserResult" when {
      val actionsMap      = Map[String, Action](("open", Actions.Open), ("close", Actions.Close))
      val genericItemRef  = new ItemRef {}
      val genericItemRef2 = new ItemRef {}
      val itemsMap =
        Map[String, ItemRef](("myItem", genericItemRef), ("myItemAlias", genericItemRef), ("myItem2", genericItemRef2))
      val resolver = Resolver(actionsMap, itemsMap)

      "parser result is intransitive" should {
        val parserResult = new ParserResult() {
          override def tree: AST = AST.Intransitive("open", "")
        }
        val statement = for {
          resolverResult <- resolver resolve parserResult
        } yield resolverResult.statement
        "produce an intransitive statement" in {

          assert(statement.isRight, "Resolver has not produced any statement")
          assert(
            statement.exists {
              case Statement.Intransitive(_) => true
              case _                         => false
            },
            "Resolver has not produced an intransitive statement"
          )

        }
        "contain expected values in content" in {
          assert(
            statement.contains(Statement.Intransitive(actionsMap("open"))),
            "Resolver has not produced " +
              "the correct statement"
          )
        }
      }

      "parser result is transitive" should {
        val parserResult = new ParserResult() {
          override def tree: AST = AST.Transitive("close", "", "myItem")
        }
        val statement = for {
          resolverResult <- resolver resolve parserResult
        } yield resolverResult.statement

        "produce a transitive statement" in {

          assert(statement.isRight, "Resolver has not produced any statement")
          assert(
            statement.exists {
              case Statement.Transitive(_, _) => true
              case _                          => false
            },
            "Resolver has not produced a transitive statement"
          )

        }

        "contain expected values in content" in {
          assert(
            statement.contains(Statement.Transitive(actionsMap("close"), itemsMap("myItem"))),
            "Resolver has not produced " +
              "the correct statement"
          )

        }
      }

      "parser result is ditransitive" should {
        val parserResult = new ParserResult() {
          override def tree: AST = AST.Ditransitive("open", "", "myItem", "myItem2")
        }
        val statement = for {
          resolverResult <- resolver resolve parserResult
        } yield resolverResult.statement
        "produce a ditransitive statement" in {

          assert(statement.isRight, "Resolver has not produced any statement")
          assert(
            statement.exists {
              case Statement.Ditransitive(_, _, _) => true
              case _                               => false
            },
            "Resolver has not produced a ditransitive statement"
          )

        }
        "contain expected values in content" in {
          assert(
            statement.contains(Statement.Ditransitive(actionsMap("open"), itemsMap("myItem"), itemsMap("myItem2"))),
            "Resolver has not produced " +
              "the correct statement"
          )

        }
      }

      "parser result is type unknown" should {
        "fail" in {
          val parserResult = new ParserResult() {
            override def tree: AST = null
          }
          val statement = for {
            resolverResult <- resolver resolve parserResult
          } yield resolverResult.statement
          assert(
            statement.left.getOrElse("non-left") == "The statement is wrong.",
            "parser result is non empty but it should be"
          )

        }
      }
    }
  }

}
