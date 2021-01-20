package io.github.scalaquest.core.model.common.groundBehaviors.std

import io.github.scalaquest.core.TestsUtils.{simpleState, targetRoom}
import io.github.scalaquest.core.model.Direction
import io.github.scalaquest.core.model.common.Actions.Go
import org.scalatest.wordspec.AnyWordSpec
import io.github.scalaquest.core.model.std.StdModel.{BehaviorableGround, GroundBehavior, Navigation}

class NavigationTest extends AnyWordSpec {
  "A Navigation Ground Behavior" when {
    val navigation = Navigation()

    "Applied to a Ground" when {
      case object SimpleGround extends BehaviorableGround {
        override val behaviors: Seq[GroundBehavior] = Seq(navigation)
      }

      "A directional Action is provided" should {
        "move the player in the designed Room, if defined" in {
          for {
            react <- SimpleGround.use(Go(Direction.NORTH), simpleState) toRight fail(
              "Reaction not generated"
            )
            modState <- Right(react(simpleState))
            currRoom <- Right(modState.game.player.location)
          } yield assert(targetRoom == currRoom, "The player has reached the Room")
        }

        "not move the player, if a designed room is not defined" in {
          assert(
            SimpleGround.use(Go(Direction.SOUTH), simpleState).isEmpty,
            "A reaction has been generated"
          )
        }
      }
    }
  }
}
