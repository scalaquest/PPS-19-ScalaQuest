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
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class InspectableBagTest extends AnyWordSpec with Matchers {

  "An inspectableBag Behavior" when {
    val inspectable = InspectableBag()

    case object SimpleGround extends BehaviorBasedGround {
      override val behaviors: Seq[GroundBehavior] = Seq(inspectable)
    }

    "applied to a bag with some object" should {
      "describe the items in the bag" in {
        for {
          react    <- SimpleGround.use(InspectBag)(simpleState) toRight fail("Reaction not generated")
          modState <- Right(react(simpleState))
        } yield modState.messages.last shouldBe Messages.InspectedBag(Set(apple))
      }
    }

    "applied to a empty bag" should {
      "describe the bag as empty" in {

        for {
          appleEatenReact <- apple.use(Eat, None)(simpleState) toRight fail(
            "Reaction not generated"
          )
          inspectedBagReact <- SimpleGround.use(InspectBag)(
            appleEatenReact(simpleState)
          ) toRight fail("Reaction not generated")
          modState <- Right(inspectedBagReact(appleEatenReact(simpleState)))
        } yield modState.messages.last shouldBe Messages.InspectedBag(Set.empty)
      }
    }
  }
}
