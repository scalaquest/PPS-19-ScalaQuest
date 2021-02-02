package io.github.scalaquest.examples

import io.github.scalaquest.core.application.ApplicationStructure
import io.github.scalaquest.core.dictionary.Dictionary
import io.github.scalaquest.core.model.behaviorBased.impl.SimpleModel
import io.github.scalaquest.core.parsing.engine.{DCGLibrary, Engine, Theory}
import io.github.scalaquest.core.pipeline.Pipeline
import io.github.scalaquest.core.pipeline.Pipeline.PipelineBuilder
import io.github.scalaquest.core.pipeline.interpreter.Interpreter
import io.github.scalaquest.core.pipeline.lexer.SimpleLexer
import io.github.scalaquest.core.pipeline.parser.Parser
import io.github.scalaquest.core.pipeline.reducer.Reducer
import io.github.scalaquest.core.pipeline.resolver.Resolver

package object escaperoom extends ApplicationStructure(SimpleModel) {

  override def dictionary: Dictionary[Item] = MyDictionary.dictionary

  def defaultPipeline(source: String, ground: Ground): PipelineBuilder[State, Model] = {

    val b = Interpreter.builder(model)(refToItem, ground)

    val a = Reducer.builder(model)

    Pipeline
      .fromModel(model)
      .build(
        SimpleLexer,
        Parser(Engine(Theory(source), Set(DCGLibrary))),
        Resolver.builder(model),
        ???,
        ???
      )
  }
}
