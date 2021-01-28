package io.github.scalaquest.core.model.behaviorBased.common.itemBehaviors.impl

import io.github.scalaquest.core.TestsUtils.{simpleState, targetRoom}
import io.github.scalaquest.core.model.Action.Common.Enter
import io.github.scalaquest.core.model.{ItemDescription, ItemRef}
import io.github.scalaquest.core.model.behaviorBased.impl.SimpleModel.{
  SimpleDoor,
  SimpleOpenable,
  SimpleRoomLink,
  SimpleState
}
import org.scalatest.wordspec.AnyWordSpec

class SimpleRoomLinkTest extends AnyWordSpec {

  "A RoomLinkBehavior" when {

    "the item has not an openable behavior" when {
      val roomLink              = SimpleRoomLink(targetRoom)
      val doorDescription       = ItemDescription("door")
      val targetItem            = SimpleDoor(doorDescription, ItemRef(doorDescription), roomLink)
      val stateWithTargetInRoom = simpleState.copyWithItemInLocation(targetItem)

      "the user says 'enter the item'" should {

        "move the player in the designed room" in {
          for {
            react <- targetItem.use(Enter, stateWithTargetInRoom, None) toRight fail(
              "Reaction not generated"
            )
            modState <- Right(react(stateWithTargetInRoom))
          } yield assert(
            modState.matchState.player.location == targetRoom.ref,
            "The player is not in the right location"
          )
        }
      }
    }

    "the item is open" when {
      val roomLink                                 = SimpleRoomLink(targetRoom, Some(SimpleOpenable(_isOpen = true)))
      val doorDescription                          = ItemDescription("door")
      val targetItem                               = SimpleDoor(doorDescription, ItemRef(doorDescription), roomLink)
      val stateWithOpenedTargetInRoom: SimpleState = simpleState.copyWithItemInLocation(targetItem)

      "the user says 'enter the item'" should {

        "move the player in the designed room" in {
          for {
            react <- targetItem.use(Enter, stateWithOpenedTargetInRoom, None) toRight fail(
              "Reaction not generated"
            )
            modState <- Right(react(stateWithOpenedTargetInRoom))
          } yield assert(
            modState.matchState.player.location == targetRoom.ref,
            "The player is not in the right location"
          )
        }
      }
    }

    "the item is closed" when {
      val roomLink                    = SimpleRoomLink(targetRoom, Some(SimpleOpenable()))
      val doorDescription             = ItemDescription("door")
      val targetItem                  = SimpleDoor(doorDescription, ItemRef(doorDescription), roomLink)
      val stateWithClosedTargetInRoom = simpleState.copyWithItemInLocation(targetItem)
      "the user says 'enter the item'" should {
        "not move the player in the designed room" in {
          assert(
            targetItem.use(Enter, stateWithClosedTargetInRoom, None).isEmpty,
            "A reaction has been generated"
          )
        }
      }
    }
  }
}
