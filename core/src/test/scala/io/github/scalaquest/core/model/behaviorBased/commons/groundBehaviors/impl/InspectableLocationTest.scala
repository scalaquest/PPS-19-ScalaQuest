package io.github.scalaquest.core.model.behaviorBased.commons.groundBehaviors.impl

import io.github.scalaquest.core.TestsUtils
import io.github.scalaquest.core.TestsUtils.model.{
  BehaviorBasedGround,
  GroundBehavior,
  InspectableLocation,
  CMessages
}
import io.github.scalaquest.core.TestsUtils._
import io.github.scalaquest.core.model.Direction
import io.github.scalaquest.core.model.behaviorBased.commons.actioning.CActions.Inspect
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class InspectableLocationTest extends AnyWordSpec with Matchers {

  "An Inspect Ground Behavior" when {
    val inspection = InspectableLocation()

    "Applied to a Ground" when {
      case object SimpleGround extends BehaviorBasedGround {
        override val behaviors: Seq[GroundBehavior] = Seq(inspection)
      }

      "An inspect Action is provided" should {
        "describe the room, the items in it, the neighbors" in {
          val targetResult =
            CMessages.Inspected(
              startRoom,
              Set(TestsUtils.key, door),
              Map(Direction.North -> targetRoom)
            )

          for {
            react <- SimpleGround.use(Inspect)(simpleState) toRight fail(
              "Reaction not generated"
            )
            msgs <- Right(react(simpleState)._2)

          } yield msgs.last shouldBe targetResult
        }
      }
    }
  }
}
