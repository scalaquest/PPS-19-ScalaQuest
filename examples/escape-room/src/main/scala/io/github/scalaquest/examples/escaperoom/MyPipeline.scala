package io.github.scalaquest.examples.escaperoom

import cats.implicits.catsKernelStdSemilatticeForSet
import io.github.scalaquest.core.dictionary.{Program, Verb}
import io.github.scalaquest.core.dictionary.generators.{Generator, GeneratorK, combineAll}
import io.github.scalaquest.core.pipeline.Pipeline
import io.github.scalaquest.core.pipeline.Pipeline.PipelineBuilder
import io.github.scalaquest.core.pipeline.lexer.{Lexer, SimpleLexer}
import io.github.scalaquest.core.parsing.engine.{DCGLibrary, Engine, Theory}
import io.github.scalaquest.core.pipeline.interpreter.Interpreter
import io.github.scalaquest.core.pipeline.parser.Parser
import io.github.scalaquest.core.pipeline.reducer.Reducer
import io.github.scalaquest.core.pipeline.resolver.Resolver

import scala.io.Source

object MyPipeline {

  def source: String = {
    import generators.{verbListToProgram, itemListToProgram}
    val base = Source.fromResource("base.pl").mkString
    val source = base +: combineAll(
      GeneratorK[List, Verb, Program].generate(dictionary.verbs),
      GeneratorK[List, Item, Program].generate(dictionary.items)
    ).map(_.generate).toList
    println(source.mkString("\n"))
    source.mkString("\n")
  }

  val lexer: Lexer = SimpleLexer

  val parser: Parser = Parser(Engine(Theory(source), Set(DCGLibrary)))

  val resolverB: Resolver.Builder[State] = Resolver.builder(myModel)

  val interpreterB: Interpreter.Builder[Model, State, Reaction] =
    Interpreter.builder(myModel)(itemDict = items, myModel.SimpleGround)

  val reducerB: Reducer.Builder[Model, State, Reaction] = Reducer.builder(myModel)

  val pipelineBuilder: PipelineBuilder[State, Model] =
    Pipeline
      .fromModel(myModel)
      .build(
        lexer,
        parser,
        resolverB,
        interpreterB,
        reducerB
      )
}
