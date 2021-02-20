package io.github.scalaquest.core.application

import io.github.scalaquest.core.model.Model
import io.github.scalaquest.core.pipeline.Pipeline
import io.github.scalaquest.core.pipeline.interpreter.Interpreter
import io.github.scalaquest.core.pipeline.lexer.Lexer
import io.github.scalaquest.core.pipeline.parser.Parser
import io.github.scalaquest.core.pipeline.reducer.Reducer
import io.github.scalaquest.core.pipeline.resolver.Resolver

/**
 * Provides a `Pipeline`, given the required parameters.
 */
trait PipelineProvider[M0 <: Model] extends ApplicationStructure[M0] {

  /** The lexer used in the pipeline. */
  def lexer: Lexer

  /** The parser used in the pipeline. */
  def parser: Parser

  /** The resolver used in the pipeline. */
  def resolver: Resolver.Builder[S]

  /** The interpreter used in the pipeline. */
  def interpreter: Interpreter.Builder[M, S, React]

  /** The reducer used in the pipeline. */
  def reducer: Reducer.Builder[M, S, React]

  /** The pipeline built using the given components. */
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
