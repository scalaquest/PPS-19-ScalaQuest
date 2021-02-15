package io.github.scalaquest.core.application

import cats.Functor
import io.github.scalaquest.core.dictionary.Dictionary
import io.github.scalaquest.core.dictionary.generators.GeneratorK
import io.github.scalaquest.core.dictionary.verbs.{Verb, VerbPrep}
import io.github.scalaquest.core.model.{Action, ItemRef, Model}
import io.github.scalaquest.core.parsing.engine.{DCGLibrary, Engine, Theory}
import io.github.scalaquest.core.pipeline.Pipeline
import io.github.scalaquest.core.pipeline.interpreter.Interpreter
import io.github.scalaquest.core.pipeline.interpreter.Interpreter.Builder
import io.github.scalaquest.core.pipeline.lexer.SimpleLexer
import io.github.scalaquest.core.pipeline.parser.Parser
import io.github.scalaquest.core.pipeline.reducer.Reducer
import io.github.scalaquest.core.pipeline.resolver.Resolver

import scala.io.Source

trait ApplicationStructure[MM <: Model] {
  import io.github.scalaquest.core.dictionary.generators.implicits.listToMapGenerator
  import io.github.scalaquest.core.dictionary.implicits.{itemToEntryGenerator, verbToEntryGenerator}

  val model: MM

  type M        = model.type
  type S        = model.S
  type I        = model.I
  type RM       = model.RM
  type G        = model.G
  type Reaction = model.Reaction

  def verbs: Set[Verb]

  def items: Set[I]

  def refToItem: Map[ItemRef, I] = GeneratorK[List, I, Map[ItemRef, I]].generate(items.toList)

  def verbToAction: Map[VerbPrep, Action] =
    GeneratorK[List, Verb, Map[VerbPrep, Action]].generate(verbs.toList)

  def programSource[F[_]: Functor](base: F[String]): F[String] =
    ProgramFromDictionary(verbs, items).source(base)

  def programFromResource(resourceName: String): String = {
    type Id[X] = X
    programSource[Id](Source.fromResource(resourceName).mkString)
  }

  def defaultPipeline(source: String): Pipeline.PartialBuilder[S, M] = {
    Pipeline
      .builderFrom[M](model)
      .build(
        SimpleLexer,
        Parser(Engine(Theory(source), Set(DCGLibrary))),
        Resolver.builder(model),
        Interpreter.builder(model),
        Reducer.builder(model)
      )
  }

}
