package io.github.scalaquest.core.model.behaviorBased.commons.groundBehaviors.impl

import io.github.scalaquest.core.TestsUtils.model.{
  BehaviorBasedGround,
  GroundBehavior,
  InspectableLocation,
  Messages
}
import io.github.scalaquest.core.TestsUtils._
import io.github.scalaquest.core.model.Direction
import io.github.scalaquest.core.model.behaviorBased.commons.actioning.CommonActions.Inspect
import org.scalatest.wordspec.AnyWordSpec

class InspectableTest extends AnyWordSpec {

  "An Inspect Ground Behavior" when {
    val inspection = InspectableLocation()

    "Applied to a Ground" when {
      case object SimpleGround extends BehaviorBasedGround {
        override val behaviors: Seq[GroundBehavior] = Seq(inspection)
      }

      "An inspect Action is provided" should {
        "describe the room, the items in it, the neighbors" in {
          val targetResult =
            Messages.Inspected(startRoom, Set(key, door), Map(Direction.North -> targetRoom))

          for {
            react <- SimpleGround.use(Inspect)(simpleState) toRight fail(
              "Reaction not generated"
            )
            modState <- Right(react(simpleState))

          } yield assert(
            modState.messages.last == targetResult,
            "The player has reached the Room"
          )
        }
      }
    }
  }
}
