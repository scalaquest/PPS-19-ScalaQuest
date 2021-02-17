package io.github.scalaquest.core.application

import io.github.scalaquest.core.model.Model
import io.github.scalaquest.core.parsing.engine.{DCGLibrary, Engine, Theory}
import io.github.scalaquest.core.pipeline.interpreter.Interpreter
import io.github.scalaquest.core.pipeline.interpreter.Interpreter.Builder
import io.github.scalaquest.core.pipeline.lexer.{Lexer, SimpleLexer}
import io.github.scalaquest.core.pipeline.parser.Parser
import io.github.scalaquest.core.pipeline.reducer.Reducer
import io.github.scalaquest.core.pipeline.resolver.Resolver

trait DefaultPipelineProvider[M0 <: Model] extends PipelineProvider[M0] {

  def baseTheory: String

  override def lexer: Lexer = SimpleLexer

  override def parser: Parser = Parser(Engine(Theory(baseTheory), Set(DCGLibrary)))

  override def resolver: Resolver.Builder[S] = Resolver.builder(model)

  override def interpreter: Builder[M, S, Reaction] = Interpreter.builder(model)

  override def reducer: Reducer.Builder[M, S, Reaction] = Reducer.builder(model)
}
