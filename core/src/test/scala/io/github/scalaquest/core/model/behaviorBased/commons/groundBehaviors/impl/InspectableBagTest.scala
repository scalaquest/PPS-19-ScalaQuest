package io.github.scalaquest.core.model.behaviorBased.commons.groundBehaviors.impl

import io.github.scalaquest.core.TestsUtils._
import io.github.scalaquest.core.TestsUtils.model.{
  BehaviorBasedGround,
  GroundBehavior,
  InspectableBag,
  Messages
}
import io.github.scalaquest.core.model.behaviorBased.commons.actioning.CommonActions.{
  Eat,
  InspectBag
}
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper
import org.scalatest.wordspec.AnyWordSpec

class InspectableBagTest extends AnyWordSpec {

  "An inspect bag Behavior" when {
    val inspection = InspectableBag()

    case object SimpleGround extends BehaviorBasedGround {
      override val behaviors: Seq[GroundBehavior] = Seq(inspection)
    }

    "applied to a bag with some object" must {
      "describe the objects presents in the bag" in {

        val targetResult =
          Messages.InspectedBag(Set(apple))

        for {
          react <- SimpleGround.use(InspectBag)(simpleState) toRight fail(
            "Reaction not generated"
          )
          modState <- Right(react(simpleState))

        } yield modState.messages.last shouldBe targetResult
      }

    }

    "applied to a empty bag" must {
      "return that the bag is empty" in {

        val targetResult =
          Messages.InspectedBag(Set.empty)

        for {
          appleEaten <- apple.use(Eat, None)(simpleState) toRight fail(
            "Reaction not generated"
          )

          react <- SimpleGround.use(InspectBag)(appleEaten(simpleState)) toRight fail(
            "Reaction not generated"
          )
          modState <- Right(react(appleEaten(simpleState)))

        } yield modState.messages.last shouldBe targetResult

      }
    }

  }

}
