package io.github.scalaquest.core.pipeline.reducer

import io.github.scalaquest.core.TestsUtils.{simpleState, apple}
import io.github.scalaquest.core.model.std.StdModel
import io.github.scalaquest.core.model.std.StdModel.bagLens
import io.github.scalaquest.core.pipeline.interpreter.InterpreterResult
import org.scalatest.wordspec.AnyWordSpec

class ReducerTest extends AnyWordSpec {
  "A Reducer" when {
    val reducer = Reducer(StdModel)(simpleState)

    "given a Reaction" should {
      // an Interpreter Result with a Reaction that adds an apple to the bag
      val interpreterResult = InterpreterResult(StdModel)(bagLens.modify(_ + apple)(_))
      val desiredState      = bagLens.modify(_ + apple)(simpleState)

      "return a State modified accordingly" in {
        assert(
          reducer.reduce(interpreterResult).state == desiredState,
          "the State has not been modified accordingly"
        )
      }
    }

    "An ReducerBuilder" should {
      import org.scalatest.matchers.should.Matchers.{a, convertToAnyShouldWrapper}

      "be of the right type" in {
        val builder = Reducer.builder(StdModel)
        builder shouldBe a[Reducer.Builder[_, _, _]]

        val reducer = builder(simpleState)
        reducer shouldBe a[Reducer[_, _, _]]
      }
    }
  }
}
