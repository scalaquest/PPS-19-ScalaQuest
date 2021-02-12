package io.github.scalaquest.core.model.behaviorBased.commons.groundBehaviors.impl

import io.github.scalaquest.core.TestsUtils
import TestsUtils.{door, key, simpleState, startRoom, targetRoom}
import TestsUtils.model.{Inspectable, BehaviorBasedGround, GroundBehavior, Messages}
import io.github.scalaquest.core.model.Action.Common.Inspect
import io.github.scalaquest.core.model.Direction
import org.scalatest.wordspec.AnyWordSpec

class InspectableTest extends AnyWordSpec {

  "An Inspect Ground Behavior" when {
    val inspection = Inspectable()

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
