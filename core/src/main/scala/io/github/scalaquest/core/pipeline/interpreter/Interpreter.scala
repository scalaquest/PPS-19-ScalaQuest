package io.github.scalaquest.core.pipeline.interpreter

import io.github.scalaquest.core.model.Model
import io.github.scalaquest.core.pipeline.resolver.ResolverResult
import io.github.scalaquest.core.pipeline.resolver.Statement.{Ditransitive, Intransitive, Transitive}

trait Interpreter[M <: Model] {
  def interpret(resolverResult: ResolverResult): Either[String, InterpreterResult[M]]
}

trait InterpreterResult[M <: Model] {
  def reaction: M#Reaction
}

abstract class TypedInterpreter[M <: Model](val model: M) {
  type Reaction = model.Reaction
  type S        = model.S
  type I        = model.I

  case class SimpleInterpreterResult(reaction: Reaction) extends InterpreterResult[M]

  object InterpreterResult {
    def apply(reaction: Reaction): InterpreterResult[M] = SimpleInterpreterResult(reaction)
  }

  case class SimpleInterpreter(state: S) extends Interpreter[M] {
    private val useIntransitive: Option[Reaction] = ???

    override def interpret(resolverResult: ResolverResult): Either[String, InterpreterResult[M]] = {
      val eventualReaction: Either[String, Reaction] = resolverResult.statement match {
        case Intransitive(action) =>
          useIntransitive toRight
            s"Could not recognize ${action.name}"

        case Transitive(action, item) =>
          item.useTransitive4[model.type](action, state).toRight(s"Could not recognize ${action.name} on ${item.name}")

        /*
        case Ditransitive(action, mainItem, sideItem) =>
          mainItem.useDitransitive(action, sideItem, state) toRight
            s"Could not recognize ${action.name} on ${mainItem.name} with ${sideItem.name}"
         */
      }

      eventualReaction.map(InterpreterResult(_))
    }
  }
}
