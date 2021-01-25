package io.github.scalaquest.core.model.behaviorBased.common.itemBehaviors.impl

import io.github.scalaquest.core.TestsUtils.{simpleState, startRoom, targetRoom}
import io.github.scalaquest.core.model.Action.Common.Enter
import io.github.scalaquest.core.model.{ItemDescription, ItemRef}
import io.github.scalaquest.core.model.behaviorBased.impl.SimpleModel.{
  Door,
  SimpleDoor,
  SimpleOpenable,
  SimpleRoomLink,
  SimpleState,
  itemsLens,
  matchRoomsLens,
  roomLens
}
import org.scalatest.wordspec.AnyWordSpec

class SimpleRoomLinkTest extends AnyWordSpec {

  "A RoomLinkBehavior" when {

    "the item has not an openable behavior" when {
      val roomLink   = SimpleRoomLink(targetRoom)
      val targetItem = SimpleDoor(ItemDescription("door"), ItemRef(), roomLink)

      val stateWithTarget    = itemsLens.modify(_ + targetItem)(simpleState)
      val currRoomWithTarget = roomLens.modify(_ + targetItem.id)(startRoom)
      val stateWithTargetInRoom: SimpleState =
        matchRoomsLens.modify(_ + currRoomWithTarget)(stateWithTarget)

      "the user says 'enter the item'" should {

        "move the player in the designed room" in {
          for {
            react <- targetItem.use(Enter, stateWithTargetInRoom, None) toRight fail(
              "Reaction not generated"
            )
            modState <- Right(react(stateWithTargetInRoom))
          } yield assert(
            modState.matchState.player.location == targetRoom.id,
            "The player is not in the right location"
          )
        }
      }
    }

    "the item is open" when {
      val roomLink   = SimpleRoomLink(targetRoom, Some(SimpleOpenable(_isOpen = true)))
      val targetItem = SimpleDoor(ItemDescription("door"), ItemRef(), roomLink)

      val stateWithTarget    = itemsLens.modify(_ + targetItem)(simpleState)
      val currRoomWithTarget = roomLens.modify(_ + targetItem.id)(startRoom)
      val stateWithOpenedTargetInRoom: SimpleState =
        matchRoomsLens.modify(_ + currRoomWithTarget)(stateWithTarget)

      "the user says 'enter the item'" should {

        "move the player in the designed room" in {
          for {
            react <- targetItem.use(Enter, stateWithOpenedTargetInRoom, None) toRight fail(
              "Reaction not generated"
            )
            modState <- Right(react(stateWithOpenedTargetInRoom))
          } yield assert(
            modState.matchState.player.location == targetRoom.id,
            "The player is not in the right location"
          )
        }
      }
    }

    "the item is closed" when {
      val roomLink   = SimpleRoomLink(targetRoom, Some(SimpleOpenable()))
      val targetItem = SimpleDoor(ItemDescription("door"), ItemRef(), roomLink)

      val stateWithTarget    = itemsLens.modify(_ + targetItem)(simpleState)
      val currRoomWithTarget = roomLens.modify(_ + targetItem.id)(startRoom)
      val stateWithClosedTargetInRoom: SimpleState =
        matchRoomsLens.modify(_ + currRoomWithTarget)(stateWithTarget)
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
