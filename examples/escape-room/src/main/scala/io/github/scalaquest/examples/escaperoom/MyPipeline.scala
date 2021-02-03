package io.github.scalaquest.examples.escaperoom

import io.github.scalaquest.core.pipeline.Pipeline
import io.github.scalaquest.core.pipeline.Pipeline.PipelineBuilder
import io.github.scalaquest.core.pipeline.lexer.{Lexer, SimpleLexer}
import io.github.scalaquest.core.parsing.engine.{DCGLibrary, Engine, Theory}
import io.github.scalaquest.core.pipeline.interpreter.Interpreter
import io.github.scalaquest.core.pipeline.parser.Parser
import io.github.scalaquest.core.pipeline.reducer.Reducer
import io.github.scalaquest.core.pipeline.resolver.Resolver

object MyPipeline {

//   val lexer: Lexer = SimpleLexer
//
//   val parser: Parser = Parser(Engine(Theory(programFromResource("base.pl")), Set(DCGLibrary)))
//
//   val resolverB: Resolver.Builder[State] = Resolver.builder(model)
//
//   val interpreterB: Interpreter.Builder[Model, State, Reaction] =
//     Interpreter.builder(model)(refToItem, model.SimpleGround)
//
//   val reducerB: Reducer.Builder[Model, State, Reaction] = Reducer.builder(model)
//
//   val pipelineBuilder: PipelineBuilder[State, Model] =
//     Pipeline
//       .fromModel(model)
//       .build(
//         lexer,
//         parser,
//         resolverB,
//         interpreterB,
//         reducerB
//       )
}
