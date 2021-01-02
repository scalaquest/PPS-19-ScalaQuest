package io.github.scalaquest.core.pipeline.interpreter

import io.github.scalaquest.core.model.Model
import io.github.scalaquest.core.pipeline.resolver.ResolverResult
import io.github.scalaquest.core.pipeline.resolver.Statement.{Ditransitive, Intransitive, Transitive}

trait Interpreter[M <: Model] {
  def interpret(resolverResult: ResolverResult): Either[String, InterpreterResult[M]]
}

trait InterpreterResult[M <: Model] {
  def update: M#Update
}

abstract class TypedInterpreter[M <: Model](val model: M) {
  type Update = model.Update
  type S      = model.S
  type I      = model.I

  case class SimpleInterpreterResult(update: Update) extends InterpreterResult[M]

  object InterpreterResult {
    def apply(update: Update): InterpreterResult[M] = SimpleInterpreterResult(update)
  }

  case class SimpleInterpreter(state: S) extends Interpreter[M] {
    private val useIntransitive: Option[Update] = ???

    override def interpret(resolverResult: ResolverResult): Either[String, InterpreterResult[M]] = {
      val eventualUpdate: Either[String, Update] = resolverResult.statement match {
        case Intransitive(action) =>
          useIntransitive toRight
            s"Could not recognize ${action.name}"

        case Transitive(action, item) =>
          val aa: Option[Update] = item.useTransitive[S, Update](action, state: S)
          aa.toRight(s"Could not recognize ${action.name} on ${item.name}")

        case Ditransitive(action, mainItem, sideItem) =>
          mainItem.useDitransitive(action, sideItem, state) toRight
            s"Could not recognize ${action.name} on ${mainItem.name} with ${sideItem.name}"
      }

      eventualUpdate.map(InterpreterResult(_))
    }
  }
}
