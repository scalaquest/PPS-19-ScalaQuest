package io.github.scalaquest.core.model.common.behaviors.std

import io.github.scalaquest.core.model.Direction.Direction
import io.github.scalaquest.core.model.Room
import io.github.scalaquest.core.model.common.Actions.{Enter, Open}
import io.github.scalaquest.core.model.std.StdModel.{Door, Key, Openable, RoomLink, StdState, bagLens, itemsLens}
import org.scalatest.wordspec.AnyWordSpec

class RoomLinkTest extends AnyWordSpec {

  "A RoomLinkBehavior" when {
    val targetRoom: Room      = Room("targetRoom", () => Map[Direction, Room]())
    val simpleState: StdState = BehaviorsTestsUtils.roomsLens.modify(_ + targetRoom)(BehaviorsTestsUtils.simpleState)

    "the item has not an openable behavior" when {
      val roomLink     = RoomLink(targetRoom)
      val targetPortal = Door("roomLink", roomLink)
      val stateWPort: StdState =
        itemsLens.modify(_ + (BehaviorsTestsUtils.startRoom -> Set(targetPortal)))(simpleState)

      "the user says 'enter the item'" should {

        "move the player in the designed room" in {
          for {
            react    <- targetPortal.use(Enter, stateWPort, None) toRight fail("Reaction not generated")
            modState <- Right(react(stateWPort))
          } yield assert(modState.game.player.location == targetRoom, "The player is not in the right location")
        }
      }
    }

    "the item is open" when {
      val roomLink     = RoomLink(targetRoom, Some(Openable(_isOpen = true)))
      val targetPortal = Door("roomLink", roomLink)
      val stateWOpenPort: StdState =
        itemsLens.modify(_ + (BehaviorsTestsUtils.startRoom -> Set(targetPortal)))(simpleState)

      "the user says 'enter the item'" should {

        "move the player in the designed room" in {
          for {
            react    <- targetPortal.use(Enter, stateWOpenPort, None) toRight fail("Reaction not generated")
            modState <- Right(react(stateWOpenPort))
          } yield assert(modState.game.player.location == targetRoom, "The player is not in the right location")
        }
      }
    }

    "the item is closed" when {
      val roomLink     = RoomLink(targetRoom, Some(Openable()))
      val targetPortal = Door("roomLink", roomLink)
      val stateWClosedPort: StdState =
        itemsLens.modify(_ + (BehaviorsTestsUtils.startRoom -> Set(targetPortal)))(simpleState)
      "the user says 'enter the item'" should {
        "not move the player in the designed room" in {
          assert(targetPortal.use(Enter, stateWClosedPort, None).isEmpty, "A reaction has been generated")
        }
      }
    }
  }
}
