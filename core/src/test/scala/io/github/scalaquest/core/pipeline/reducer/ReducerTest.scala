package io.github.scalaquest.core.pipeline.reducer

import io.github.scalaquest.core.TestsUtils.{apple, simpleState, startRoom}
import io.github.scalaquest.core.model.behaviorBased.impl.SimpleModel.{
  itemsLens,
  matchRoomsLens,
  playerBagLens,
  roomLens
}
import io.github.scalaquest.core.model.behaviorBased.impl.SimpleModel
import io.github.scalaquest.core.pipeline.interpreter.InterpreterResult
import org.scalatest.wordspec.AnyWordSpec

class ReducerTest extends AnyWordSpec {
  "A Reducer" when {
    val reducer = Reducer.builder(SimpleModel)(simpleState)

    "given a Reaction" should {
      // an Interpreter Result with a Reaction that adds an apple to the bag
      val stateWithTarget   = itemsLens.modify(_ + apple)(simpleState)
      val interpreterResult = InterpreterResult(SimpleModel)(playerBagLens.modify(_ + apple.id)(_))
      val desiredState      = playerBagLens.modify(_ + apple.id)(simpleState)

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
