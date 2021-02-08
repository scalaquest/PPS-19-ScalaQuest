package io.github.scalaquest.core.model.behaviorBased.commons.groundBehaviors.impl

import io.github.scalaquest.core.TestsUtils
import io.github.scalaquest.core.model.Action.Common.Go
import io.github.scalaquest.core.model.Direction
import org.scalatest.wordspec.AnyWordSpec

class NavigableTest extends AnyWordSpec {
  import TestsUtils._
  import TestsUtils.model._

  "A Navigation Ground Behavior" when {
    val navigation = Navigable()

    "Applied to a Ground" when {
      case object SimpleGround extends BehaviorBasedGround {
        override val behaviors: Seq[GroundBehavior] = Seq(navigation)
      }

      "A directional Action is provided" should {
        "move the player in the designed Room, if defined" in {
          for {
            react <- SimpleGround.use(Go(Direction.North))(simpleState) toRight fail(
              "Reaction not generated"
            )
            modState <- Right(react(simpleState))
            currRoom <- Right(modState.location)
          } yield assert(targetRoom == currRoom, "The player has reached the Room")
        }

        "not move the player, if a designed room is not defined" in {
          assert(
            SimpleGround.use(Go(Direction.South))(simpleState).isEmpty,
            "A reaction has been generated"
          )
        }
      }
    }
  }
}
