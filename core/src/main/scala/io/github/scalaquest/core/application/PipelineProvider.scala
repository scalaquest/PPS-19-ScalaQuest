package io.github.scalaquest.core.application

import io.github.scalaquest.core.model.Model
import io.github.scalaquest.core.pipeline.Pipeline
import io.github.scalaquest.core.pipeline.interpreter.Interpreter
import io.github.scalaquest.core.pipeline.lexer.Lexer
import io.github.scalaquest.core.pipeline.parser.Parser
import io.github.scalaquest.core.pipeline.reducer.Reducer
import io.github.scalaquest.core.pipeline.resolver.Resolver

trait PipelineProvider[M0 <: Model] extends ApplicationStructure[M0] {

  def lexer: Lexer

  def parser: Parser

  def resolver: Resolver.Builder[S]

  def interpreter: Interpreter.Builder[M, S, Reaction]

  def reducer: Reducer.Builder[M, S, Reaction]

  def makePipeline: Pipeline.PartialBuilder[S, M] =
    Pipeline
      .builderFrom[M](model)
      .build(
        lexer,
        parser,
        resolver,
        interpreter,
        reducer
      )

}
