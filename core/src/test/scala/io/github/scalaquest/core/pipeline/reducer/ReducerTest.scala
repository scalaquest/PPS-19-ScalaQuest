package io.github.scalaquest.core.pipeline.reducer

import io.github.scalaquest.core.TestsUtils.{apple, simpleState}
import io.github.scalaquest.core.model.behaviorBased.simple.SimpleModel.bagLens
import io.github.scalaquest.core.model.behaviorBased.simple.SimpleModel
import io.github.scalaquest.core.pipeline.interpreter.InterpreterResult
import org.scalatest.wordspec.AnyWordSpec

class ReducerTest extends AnyWordSpec {
  "A Reducer" when {
    val reducer = Reducer.builder(SimpleModel)(simpleState)

    "given a Reaction" should {
      // an Interpreter Result with a Reaction that adds an apple to the bag
      val interpreterResult = InterpreterResult(SimpleModel)(bagLens.modify(_ + apple.ref)(_))
      val desiredState      = bagLens.modify(_ + apple.ref)(simpleState)

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
        val builder = Reducer.builder(SimpleModel)
        builder shouldBe a[Reducer.Builder[_, _, _]]

        val reducer = builder(simpleState)
        reducer shouldBe a[Reducer[_, _, _]]
      }
    }
  }
}
