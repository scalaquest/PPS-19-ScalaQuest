package io.github.scalaquest.core.model.behaviorBased.commons.itemBehaviors.impl

import io.github.scalaquest.core.TestsUtils
import io.github.scalaquest.core.model.behaviorBased.commons.actioning.CommonActions.{Enter, Open}
import io.github.scalaquest.core.model.{Direction, ItemDescription, ItemRef}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import TestsUtils._
import TestsUtils.model._

class RoomLinkTest extends AnyWordSpec with Matchers {

  "A RoomLinkBehavior" when {

    "the item has not an openable behavior" when {
      val targetItem      = Door(ItemDescription("door"), RoomLink.builder(targetRoom, Direction.North))
      val stateWithTarget = simpleState.copyWithItemInLocation(targetItem)

      "the user says 'enter the item'" should {
        "move the player in the designed room" in {
          for {
            enteredReact <- targetItem.use(Enter, None)(stateWithTarget) toRight fail(
              "Reaction not generated"
            )
            modState <- Right(enteredReact(stateWithTarget)._1)
            msgs     <- Right(enteredReact(stateWithTarget)._2)
          } yield {
            modState.location shouldBe targetRoom
            msgs should contain(Messages.Navigated(targetRoom))
          }
        }
      }
    }

    "the item is closed" when {
      val roomLink              = RoomLink.builder(targetRoom, Direction.North, Some(Openable.unlockedBuilder()))
      val doorDescription       = ItemDescription("door")
      val targetItem            = SimpleDoor(doorDescription, ItemRef(doorDescription), roomLink)
      val stateWithClosedTarget = simpleState.copyWithItemInLocation(targetItem)
      "the user says 'enter the item'" should {
        "not move the player in the designed room" in {
          for {
            enteredReact <- targetItem.use(Enter)(stateWithClosedTarget) toRight fail(
              "Reaction not generated"
            )
            modState <- Right(enteredReact(stateWithClosedTarget)._1)
            msgs     <- Right(enteredReact(stateWithClosedTarget)._2)
          } yield {
            modState.location should not be targetRoom
            msgs should contain(Messages.FailedToEnter(targetItem))
          }
        }
      }

      "the user first says 'open the item', then 'enter the item'" should {
        "move the player in the designed room" in {
          for {
            openReact <- targetItem.use(Open)(stateWithClosedTarget) toRight fail(
              "Open reaction not generated"
            )
            enteredReact <- targetItem.use(Enter)(
              openReact(stateWithClosedTarget)._1
            ) toRight fail(
              "Enter reaction not generated"
            )
            modState <- Right(enteredReact(openReact(stateWithClosedTarget)._1)._1)
            msgs     <- Right(enteredReact(openReact(stateWithClosedTarget)._1)._2)
          } yield {
            msgs should contain(Messages.Navigated(targetRoom))
            modState.location shouldBe targetRoom
          }
        }
      }
    }
  }
}
