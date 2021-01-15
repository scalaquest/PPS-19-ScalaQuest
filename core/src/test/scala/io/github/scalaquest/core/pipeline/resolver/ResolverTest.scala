package io.github.scalaquest.core.pipeline.resolver

import io.github.scalaquest.core.model.Action
import io.github.scalaquest.core.model.common.Actions
import io.github.scalaquest.core.pipeline.interpreter.ItemRef
import io.github.scalaquest.core.pipeline.parser.{AST, ParserResult}
import org.scalatest.wordspec.AnyWordSpec

class ResolverTest extends AnyWordSpec {
  "A Resolver" when {
    "receives a ParserResult" when {
      val actionsMap = Map[String, Action](("open", Actions.Open), ("close", Actions.Close))
      val itemsMap   = Map[String, ItemRef]() //Map[String,ItemRef](("door",ItemRetriever()))
      val resolver   = Resolver(actionsMap, itemsMap)

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
        "intransitive statement's content " must {
          "be correct" in {
            assert(
              statement.contains(Statement.Intransitive(actionsMap("open"))),
              "Resolver has not produced " +
                "the correct statement"
            )
          }
        }

      }
    }
  }
}
