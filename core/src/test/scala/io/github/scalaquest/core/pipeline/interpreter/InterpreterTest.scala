package io.github.scalaquest.core.pipeline.interpreter

import io.github.scalaquest.core.TestsUtils.{
  appleItemRef,
  doorItemRef,
  key,
  keyItemRef,
  refItemDictionary,
  roomLinkDoor,
  simpleState,
  startRoom,
  takeableApple
}
import io.github.scalaquest.core.model.common.Actions.{GoNorth, Open, Take}
import io.github.scalaquest.core.model.std.StdModel
import io.github.scalaquest.core.model.std.StdModel.{StdGround, itemsLens}
import io.github.scalaquest.core.pipeline.resolver.{ResolverResult, Statement}
import org.scalatest.wordspec.AnyWordSpec

class InterpreterTest extends AnyWordSpec {
  "An Interpreter" when {
    val interpreter = Interpreter(StdModel)(simpleState, refItemDictionary, StdGround)

    "given an Intransitive Statement" should {
      val resolverResult   = ResolverResult(Statement.Intransitive(GoNorth))
      val maybeExpReaction = StdGround.use(GoNorth, simpleState)

      "return the right Reaction" in {
        checkResult(interpreter, resolverResult, maybeExpReaction)
      }
    }

    "given a Transitive Statement" should {
      val resolverResult   = ResolverResult(Statement.Transitive(Take, appleItemRef))
      val stateItemInRoom  = itemsLens.modify(_ + (startRoom -> Set(takeableApple)))(simpleState)
      val maybeExpReaction = takeableApple.use(Take, stateItemInRoom, None)

      "return the right Reaction" in {
        checkResult(interpreter, resolverResult, maybeExpReaction)
      }
    }

    "given a Ditransitive Statement" should {
      val resolverResult   = ResolverResult(Statement.Ditransitive(Open, doorItemRef, keyItemRef))
      val stateItemInRoom  = itemsLens.modify(_ + (startRoom -> Set(roomLinkDoor, key)))(simpleState)
      val maybeExpReaction = roomLinkDoor.use(Open, stateItemInRoom, Some(key))

      "return the right Reaction" in {
        checkResult(interpreter, resolverResult, maybeExpReaction)
      }
    }
  }

  def checkResult(
    interpreter: Interpreter[StdModel.type, StdModel.Reaction],
    resolverResult: ResolverResult,
    maybeExpReaction: Option[StdModel.Reaction]
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
      val builder = Interpreter.builder(StdModel)(refItemDictionary, StdGround)
      builder shouldBe a[Interpreter.Builder[_, _, _]]

      val interpreter = builder(simpleState)
      interpreter shouldBe a[Interpreter[_, _]]
    }
  }
}
