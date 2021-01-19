package io.github.scalaquest.core.model.common.behaviors.std

import io.github.scalaquest.core.TestsUtils.{roomsLens, simpleState, startRoom, targetRoom}
import io.github.scalaquest.core.model.ItemRef
import io.github.scalaquest.core.model.common.Actions.Enter
import io.github.scalaquest.core.model.std.StdModel.{Door, Openable, RoomLink, StdState, itemsLens}
import org.scalatest.wordspec.AnyWordSpec

class RoomLinkTest extends AnyWordSpec {

  "A RoomLinkBehavior" when {
    val stateWithRoom: StdState =
      roomsLens.modify(_ + targetRoom)(simpleState)

    "the item has not an openable behavior" when {
      val roomLink     = RoomLink(targetRoom)
      val targetPortal = Door(new ItemRef {}, roomLink)
      val stateWPort: StdState =
        itemsLens.modify(_ + (startRoom -> Set(targetPortal)))(stateWithRoom)

      "the user says 'enter the item'" should {

        "move the player in the designed room" in {
          for {
            react <- targetPortal.use(Enter, stateWPort, None) toRight fail(
              "Reaction not generated"
            )
            modState <- Right(react(stateWPort))
          } yield assert(
            modState.game.player.location == targetRoom,
            "The player is not in the right location"
          )
        }
      }
    }

    "the item is open" when {
      val roomLink     = RoomLink(targetRoom, Some(Openable(_isOpen = true)))
      val targetPortal = Door(new ItemRef {}, roomLink)
      val stateWOpenPort: StdState =
        itemsLens.modify(_ + (startRoom -> Set(targetPortal)))(stateWithRoom)

      "the user says 'enter the item'" should {

        "move the player in the designed room" in {
          for {
            react <- targetPortal.use(Enter, stateWOpenPort, None) toRight fail(
              "Reaction not generated"
            )
            modState <- Right(react(stateWOpenPort))
          } yield assert(
            modState.game.player.location == targetRoom,
            "The player is not in the right location"
          )
        }
      }
    }

    "the item is closed" when {
      val roomLink     = RoomLink(targetRoom, Some(Openable()))
      val targetPortal = Door(new ItemRef {}, roomLink)
      val stateWClosedPort: StdState =
        itemsLens.modify(_ + (startRoom -> Set(targetPortal)))(stateWithRoom)
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
