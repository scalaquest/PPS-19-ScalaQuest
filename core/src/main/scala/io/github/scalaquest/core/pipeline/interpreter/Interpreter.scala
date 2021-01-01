package io.github.scalaquest.core.pipeline.interpreter

import io.github.scalaquest.core.model.Model
import io.github.scalaquest.core.pipeline.resolver.ResolverResult
import io.github.scalaquest.core.pipeline.resolver.Statement.{Intransitive, Transitive, Ditransitive}

trait Interpreter {
  def interpret(resolverResult: ResolverResult): Either[String, InterpreterResult]
}

case class SimpleInterpreter(state: Model#State) extends Interpreter {
  val useIntransitive: Option[Model#Update] = ???

  override def interpret(resolverResult: ResolverResult): Either[String, InterpreterResult] = {
    val eventualUpdate = resolverResult.statement match {
      case Intransitive(action) =>
        useIntransitive toRight
          s"Could not recognize ${action.name}"

      case Transitive(action, item) =>
        item.useTransitive(action, state) toRight
          s"Could not recognize ${action.name} on ${item.name}"

      case Ditransitive(action, mainItem, sideItem) =>
        mainItem.useDitransitive(action, sideItem, state) toRight
          s"Could not recognize ${action.name} on ${mainItem.name} with ${sideItem.name}"
    }

    eventualUpdate.map(InterpreterResult(_))
  }
}

object Interpreter {
  def apply(state: Model#State): Interpreter = SimpleInterpreter(state)
}
