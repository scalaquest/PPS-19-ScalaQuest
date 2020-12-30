package io.github.scalaquest.core.pipeline.interpreter

import io.github.scalaquest.core.model.Model
import io.github.scalaquest.core.pipeline.resolver.ResolverResult
import io.github.scalaquest.core.pipeline.resolver.Statement.{Intransitive, Transitive, Ditransitive}

trait InterpreterResult {
  def update: Model#Update
}

case class SimpleInterpreterResult(update: Model#Update) extends InterpreterResult

object Interpreter {
  def apply[S <: Model#State](update: Model#Update): InterpreterResult = SimpleInterpreterResult(update: Model#Update)
}

trait Interpreter[S <: Model#S] {
  def interpret(resolverResult: ResolverResult): Either[String, InterpreterResult]
}

case class SimpleInterpreter[S <: Model#S](state: S) extends Interpreter[S] {
  val useIntransitive: Option[Model#Update] = ???

  override def interpret(resolverResult: ResolverResult): Either[String, InterpreterResult] = {
    val eventualUpdate = resolverResult.statement match {
      case Intransitive(action) =>
        useIntransitive toRight
          (s"Could not recognize ${action.name}")

      case Transitive(action, item) =>
        item.useTransitive(action, state) toRight
          (s"Could not recognize ${action.name} on ${item.name}")

      case Ditransitive(action, mainItem, sideItem) =>
        mainItem.useDitransitive(action, sideItem, state) toRight
          (s"Could not recognize ${action.name} on ${mainItem.name} with ${sideItem.name}")
    }

  }
}

object Interpreter {
  def apply[S <: Model#State](state: S): Interpreter[S] = SimpleInterpreter(state)
}
