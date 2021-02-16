package io.github.scalaquest.core.application

import io.github.scalaquest.core.model.Model
import io.github.scalaquest.core.parsing.engine.{DCGLibrary, Engine, Theory}
import io.github.scalaquest.core.pipeline.Pipeline
import io.github.scalaquest.core.pipeline.interpreter.Interpreter
import io.github.scalaquest.core.pipeline.lexer.SimpleLexer
import io.github.scalaquest.core.pipeline.parser.Parser
import io.github.scalaquest.core.pipeline.reducer.Reducer
import io.github.scalaquest.core.pipeline.resolver.Resolver

trait PipelineProvider[M0 <: Model] extends ApplicationStructure[M0] {

  def baseTheory: String

  def makeEngine(theory: String): Engine = Engine(Theory(theory), Set(DCGLibrary))

  def makePipeline: Pipeline.PartialBuilder[S, M] =
    Pipeline
      .builderFrom[M](model)
      .build(
        SimpleLexer,
        Parser(makeEngine(baseTheory)),
        Resolver.builder(model),
        Interpreter.builder(model),
        Reducer.builder(model)
      )

}
