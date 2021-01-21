package io.github.scalaquest.core.model.behaviorBased.common.itemBehaviors.impl

import io.github.scalaquest.core.TestsUtils.{simpleState, startRoom, targetRoom}
import io.github.scalaquest.core.model.Action.Common.Enter
import io.github.scalaquest.core.model.ItemRef
import io.github.scalaquest.core.model.behaviorBased.impl.SimpleModel.{
  SimpleDoor,
  SimpleOpenable,
  SimpleRoomLink,
  SimpleState,
  geographyLens
}
import org.scalatest.wordspec.AnyWordSpec

class SimpleRoomLinkTest extends AnyWordSpec {

  "A RoomLinkBehavior" when {
    val stateWithRoom: SimpleState = geographyLens.modify(_ + (targetRoom -> Set()))(simpleState)

    "the item has not an openable behavior" when {
      val roomLink     = SimpleRoomLink(targetRoom)
      val targetPortal = SimpleDoor(new ItemRef {}, roomLink)
      val stateWPort: SimpleState =
        geographyLens.modify(_ + (startRoom -> Set(targetPortal)))(stateWithRoom)

      "the user says 'enter the item'" should {

        "move the player in the designed room" in {
          for {
            react <- targetPortal.use(Enter, stateWPort, None) toRight fail(
              "Reaction not generated"
            )
            modState <- Right(react(stateWPort))
          } yield assert(
            modState.matchState.player.location == targetRoom,
            "The player is not in the right location"
          )
        }
      }
    }

    "the item is open" when {
      val roomLink     = SimpleRoomLink(targetRoom, Some(SimpleOpenable(_isOpen = true)))
      val targetPortal = SimpleDoor(new ItemRef {}, roomLink)
      val stateWOpenPort: SimpleState =
        geographyLens.modify(_ + (startRoom -> Set(targetPortal)))(stateWithRoom)

      "the user says 'enter the item'" should {

        "move the player in the designed room" in {
          for {
            react <- targetPortal.use(Enter, stateWOpenPort, None) toRight fail(
              "Reaction not generated"
            )
            modState <- Right(react(stateWOpenPort))
          } yield assert(
            modState.matchState.player.location == targetRoom,
            "The player is not in the right location"
          )
        }
      }
    }

    "the item is closed" when {
      val roomLink     = SimpleRoomLink(targetRoom, Some(SimpleOpenable()))
      val targetPortal = SimpleDoor(new ItemRef {}, roomLink)
      val stateWClosedPort: SimpleState =
        geographyLens.modify(_ + (startRoom -> Set(targetPortal)))(stateWithRoom)
      "the user says 'enter the item'" should {
        "not move the player in the designed room" in {
          assert(
            targetPortal.use(Enter, stateWClosedPort, None).isEmpty,
            "A reaction has been generated"
          )
        }
      }
    }
  }
}
