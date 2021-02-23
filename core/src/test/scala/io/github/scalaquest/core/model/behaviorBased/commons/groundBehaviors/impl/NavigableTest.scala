package io.github.scalaquest.core.model.behaviorBased.commons.groundBehaviors.impl

import io.github.scalaquest.core.TestsUtils
import io.github.scalaquest.core.model.Direction
import io.github.scalaquest.core.model.behaviorBased.commons.actioning.CActions.Go
import org.scalatest.wordspec.AnyWordSpec
import TestsUtils._
import TestsUtils.model._
import org.scalatest.matchers.should.Matchers

class NavigableTest extends AnyWordSpec with Matchers {

  "A Navigation Ground Behavior" when {
    val navigation = Navigable()

    "Applied to a Ground" when {
      case object SimpleGround extends BehaviorBasedGround {
        override val behaviors: Seq[GroundBehavior] = Seq(navigation)
      }

      "A directional Action is provided" should {
        "move the player in the designed Room, if defined" in {
          for {
            react <- SimpleGround
              .use(Go(Direction.North))(simpleState)
              .toRight(fail("Reaction not generated"))
            updState <- Right(react(simpleState)._1)
            msgs     <- Right(react(simpleState)._2)
            currRoom <- Right(updState.location)
          } yield {
            currRoom shouldBe targetRoom
            msgs should contain(CMessages.Navigated(targetRoom))
          }
        }

        "not move the player, if a designed room is not defined" in {
          for {
            react <- SimpleGround.use(Go(Direction.South))(simpleState) toRight fail(
              "Reaction not generated"
            )
            updState <- Right(react(simpleState)._1)
            msgs     <- Right(react(simpleState)._2)
            currRoom <- Right(updState.location)
          } yield {
            currRoom shouldBe startRoom
            msgs should contain(CMessages.FailedToNavigate(Direction.South))
          }
        }
      }
    }
  }
}
