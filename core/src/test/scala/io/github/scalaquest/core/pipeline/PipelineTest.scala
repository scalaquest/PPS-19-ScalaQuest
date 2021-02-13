package io.github.scalaquest.core.pipeline

import io.github.scalaquest.core.TestsUtils
import io.github.scalaquest.core.parsing.engine.{DCGLibrary, Engine, Theory}
import io.github.scalaquest.core.pipeline.interpreter.Interpreter
import io.github.scalaquest.core.pipeline.lexer.SimpleLexer
import io.github.scalaquest.core.pipeline.parser.Parser
import io.github.scalaquest.core.pipeline.reducer.Reducer
import io.github.scalaquest.core.pipeline.resolver.Resolver
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class PipelineTest extends AnyWordSpec with Matchers {
  import TestsUtils._

  "A PipelineBuilder" should {
    val pipelineBuilder = Pipeline.builderFrom(model)

    val pipeline = pipelineBuilder.build(
      SimpleLexer,
      Parser(Engine(Theory(""), Set(DCGLibrary))),
      Resolver.builder(model),
      Interpreter.builder(model),
      Reducer.builder(model)
    )(simpleState)

    "create a proper Pipeline" in {
      pipeline.run("") shouldBe Left("Couldn't understand input.")
    }
  }
}
