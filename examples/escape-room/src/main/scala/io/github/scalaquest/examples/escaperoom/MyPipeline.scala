package io.github.scalaquest.examples.escaperoom

import io.github.scalaquest.core.pipeline.Pipeline
import io.github.scalaquest.core.pipeline.lexer.{Lexer, SimpleLexer}
import io.github.scalaquest.core.parsing.engine.{DCGLibrary, Engine, Theory}
import io.github.scalaquest.core.pipeline.interpreter.Interpreter
import io.github.scalaquest.core.pipeline.parser.Parser
import io.github.scalaquest.core.pipeline.reducer.Reducer
import io.github.scalaquest.core.pipeline.resolver.Resolver

import scala.io.Source

object MyPipeline {

  val source: String = {
    val s = Source.fromResource("base.pl").mkString + "\n" +
      Source.fromResource("names.pl").mkString
    s
  }

  val lexer: Lexer = SimpleLexer

  val parser: Parser = Parser(Engine(Theory(source), Set(DCGLibrary)))

  val resolverB: Resolver.Builder[State] = Resolver.builder(myModel)

  val interpreterB: Interpreter.Builder[Model, State, Reaction] =
    Interpreter.builder(myModel)(itemDict = items, myModel.SimpleGround)

  val reducerB: Reducer.Builder[Model, State, Reaction] = Reducer.builder(myModel)

  val pipelineFactory: Pipeline.PartialBuilder[State, Model] =
    Pipeline
      .builderFrom(myModel)
      .build(lexer, parser, resolverB, interpreterB, reducerB)
}
