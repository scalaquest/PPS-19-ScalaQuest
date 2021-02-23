package io.github.scalaquest.core.model.behaviorBased.commons.reactions

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import io.github.scalaquest.core.TestsUtils.model._
import io.github.scalaquest.core.TestsUtils._

class FinishGameTest extends AnyWordSpec with Matchers {
  "A FinishGame reaction" should {
    "finish the game with a win, if true" in {
      val winRes = CReactions.finishGame(win = true)(simpleState)
      winRes._2 should contain(CMessages.Won)
      winRes._1.ended shouldBe true
    }

    "finish the game with a lost, if false" in {
      val winRes = CReactions.finishGame(win = false)(simpleState)
      winRes._2 should contain(CMessages.Lost)
      winRes._1.ended shouldBe true
    }
  }
}
