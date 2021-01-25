package io.github.scalaquest.core.pipeline.interpreter

import io.github.scalaquest.core.TestsUtils.{
  apple,
  appleItemRef,
  door,
  doorItemRef,
  key,
  keyItemRef,
  refItemDictionary,
  simpleState,
  startRoom
}
import io.github.scalaquest.core.model.Action.Common.{Go, Open, Take}
import io.github.scalaquest.core.model.Room.Direction
import io.github.scalaquest.core.model.behaviorBased.impl.SimpleModel.{StdGround, matchRoomsLens}
import io.github.scalaquest.core.model.behaviorBased.impl.SimpleModel
import io.github.scalaquest.core.pipeline.resolver.{ResolverResult, Statement}
import org.scalatest.wordspec.AnyWordSpec

class InterpreterTest extends AnyWordSpec {
  "An Interpreter" when {
    val interpreter = Interpreter.builder(SimpleModel)(refItemDictionary, StdGround)(simpleState)

    "given an Intransitive Statement" should {
      val resolverResult   = ResolverResult(Statement.Intransitive(Go(Direction.North)))
      val maybeExpReaction = StdGround.use(Go(Direction.North), simpleState)

      "return the right Reaction" in {
        checkResult(interpreter, resolverResult, maybeExpReaction)
      }
    }

    "given a Transitive Statement" should {
      val resolverResult   = ResolverResult(Statement.Transitive(Take, appleItemRef))
      val stateItemInRoom  = simpleState.copyWithItemInLocation(apple)
      val maybeExpReaction = apple.use(Take, stateItemInRoom, None)

      "return the right Reaction" in {
        checkResult(interpreter, resolverResult, maybeExpReaction)
      }
    }

    "given a Ditransitive Statement" should {
      val resolverResult     = ResolverResult(Statement.Ditransitive(Open, doorItemRef, keyItemRef))
      val stateDoorInRoom    = simpleState.copyWithItemInLocation(door)
      val stateDoorKeyInRoom = stateDoorInRoom.copyWithItemInLocation(key)
      val maybeExpReaction   = door.use(Open, stateDoorKeyInRoom, Some(key))

      "return the right Reaction" in {
        checkResult(interpreter, resolverResult, maybeExpReaction)
      }
    }
  }

  def checkResult(
    interpreter: Interpreter[SimpleModel.type, SimpleModel.Reaction],
    resolverResult: ResolverResult,
    maybeExpReaction: Option[SimpleModel.Reaction]
  ): Unit = {
    for {
      interprResult  <- interpreter.interpret(resolverResult)
      toTestReaction <- Right(interprResult.reaction)
      expctReaction  <- maybeExpReaction toRight fail("Test implementation error")
      toTestState    <- Right(toTestReaction(simpleState))
      expctState     <- Right(expctReaction(simpleState))
    } yield assert(
      toTestState == expctState,
      "The result of the reaction application is not the expected one."
    )
  }

  "An interpreterBuilder" should {
    import org.scalatest.matchers.should.Matchers.{a, convertToAnyShouldWrapper}
    "be of the right type" in {
      val builder = Interpreter.builder(SimpleModel)(refItemDictionary, StdGround)
      builder shouldBe a[Interpreter.Builder[_, _, _]]

      val interpreter = builder(simpleState)
      interpreter shouldBe a[Interpreter[_, _]]
    }
  }
}
