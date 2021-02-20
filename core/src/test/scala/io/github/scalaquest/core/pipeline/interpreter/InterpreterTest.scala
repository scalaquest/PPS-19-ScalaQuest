package io.github.scalaquest.core.pipeline.interpreter

import io.github.scalaquest.core.TestsUtils
import io.github.scalaquest.core.TestsUtils._
import io.github.scalaquest.core.model.Direction
import io.github.scalaquest.core.model.behaviorBased.commons.actioning.CommonActions.{
  Go,
  Open,
  Take
}
import io.github.scalaquest.core.model.behaviorBased.simple.SimpleModel
import io.github.scalaquest.core.model.behaviorBased.simple.SimpleModel.CommonGround
import io.github.scalaquest.core.pipeline.resolver.{ResolverResult, Statement}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class InterpreterTest extends AnyWordSpec with Matchers {
  "An Interpreter" when {
    val interpreter = Interpreter.builder(SimpleModel)(simpleState)

    "given an Intransitive Statement" should {
      val resolverResult   = ResolverResult(Statement.Intransitive(Go(Direction.North)))
      val maybeExpReaction = CommonGround().use(Go(Direction.North))(simpleState)

      "return the right Reaction" in {
        checkResult(interpreter, resolverResult, maybeExpReaction)
      }
    }

    "given a Transitive Statement" should {
      val resolverResult   = ResolverResult(Statement.Transitive(Take, appleItemRef))
      val stateItemInRoom  = simpleState.copyWithItemInLocation(apple)
      val maybeExpReaction = apple.use(Take, None)(stateItemInRoom)

      "return the right Reaction" in {
        checkResult(interpreter, resolverResult, maybeExpReaction)
      }
    }

    "given a Ditransitive Statement" should {
      val resolverResult     = ResolverResult(Statement.Ditransitive(Open, doorItemRef, keyItemRef))
      val stateDoorInRoom    = simpleState.copyWithItemInLocation(door)
      val stateDoorKeyInRoom = stateDoorInRoom.copyWithItemInLocation(TestsUtils.key)
      val maybeExpReaction   = door.use(Open, Some(TestsUtils.key))(stateDoorKeyInRoom)

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
      toTestState    <- Right(toTestReaction(simpleState)._1)
      expctState     <- Right(expctReaction(simpleState)._1)
    } yield toTestState shouldBe expctState
  }

  "An interpreterBuilder" should {
    "be of the right type" in {
      val builder = Interpreter.builder(SimpleModel)
      builder shouldBe a[Interpreter.Builder[_, _, _]]

      val interpreter = builder(simpleState)
      interpreter shouldBe a[Interpreter[_, _]]
    }
  }
}
