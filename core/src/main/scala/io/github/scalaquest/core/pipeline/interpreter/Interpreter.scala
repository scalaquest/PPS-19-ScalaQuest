package io.github.scalaquest.core.pipeline.interpreter

import io.github.scalaquest.core.model.Model
import io.github.scalaquest.core.pipeline.resolver.ResolverResult
import io.github.scalaquest.core.pipeline.resolver.Statement.{Intransitive, Transitive, Ditransitive}

trait InterpreterResult {
  def effect: Model#Update
}

trait Interpreter[S <: Model#State] {
  def interpret(resolverResult: ResolverResult): Either[String, InterpreterResult]
}

case class SimpleInterpreter[S <: Model#State](state: S) extends Interpreter[S] {

  override def interpret(resolverResult: ResolverResult): Either[String, InterpreterResult] = {

    resolverResult.statement match {
      case Intransitive(action)                   => ???
      case Transitive(action, target)             => ???
      case Ditransitive(action, target1, target2) => ???
    }
  }
}

object Interpreter {
  def apply[S <: Model#State](state: S): Interpreter[S] = SimpleInterpreter(state)
}
