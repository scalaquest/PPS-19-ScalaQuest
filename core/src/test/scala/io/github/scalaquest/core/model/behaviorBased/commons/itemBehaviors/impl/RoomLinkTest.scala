package io.github.scalaquest.core.model.behaviorBased.commons.itemBehaviors.impl

import io.github.scalaquest.core.TestsUtils
import io.github.scalaquest.core.model.Action.Common.{Enter, Open}
import io.github.scalaquest.core.model.{Direction, ItemDescription, ItemRef}
import org.scalatest.wordspec.AnyWordSpec

class RoomLinkTest extends AnyWordSpec {
  import TestsUtils.model._
  import TestsUtils._

  "A RoomLinkBehavior" when {

    "the item has not an openable behavior" when {
      val roomLink              = RoomLink(targetRoom, Direction.North)
      val targetItem            = Door(ItemDescription("door"), roomLink)
      val stateWithTargetInRoom = simpleState.copyWithItemInLocation(targetItem)

      "the user says 'enter the item'" should {

        "move the player in the designed room" in {
          for {
            react <- targetItem.use(Enter, None)(stateWithTargetInRoom) toRight fail(
              "Reaction not generated"
            )
            modState <- Right(react(stateWithTargetInRoom))
          } yield assert(
            modState.location == targetRoom,
            "The player is not in the right location"
          )
        }
      }
    }

    "the item is closed" when {
      val roomLink                    = RoomLink(targetRoom, Direction.North, Some(SimpleOpenable()))
      val doorDescription             = ItemDescription("door")
      val targetItem                  = SimpleDoor(doorDescription, ItemRef(doorDescription), roomLink)
      val stateWithClosedTargetInRoom = simpleState.copyWithItemInLocation(targetItem)
      "the user says 'enter the item'" should {
        "not move the player in the designed room" in {
          assert(
            targetItem.use(Enter, None)(stateWithClosedTargetInRoom).isEmpty,
            "A reaction has been generated"
          )
        }
      }

      "the user first says 'open the item', then 'enter the item'" should {
        "move the player in the designed room" in {
          for {
            openReact <- targetItem.use(Open)(stateWithClosedTargetInRoom) toRight fail(
              "Open reaction not generated"
            )
            enteredReact <- targetItem.use(Enter)(
              openReact(stateWithClosedTargetInRoom)
            ) toRight fail(
              "Enter reaction not generated"
            )
            modState <- Right(enteredReact(openReact(stateWithClosedTargetInRoom)))
          } yield assert(
            modState.location == targetRoom,
            "The player is not in the right location"
          )
        }
      }
    }
  }
}
