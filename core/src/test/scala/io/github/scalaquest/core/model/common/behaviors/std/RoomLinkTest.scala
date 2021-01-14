package io.github.scalaquest.core.model.common.behaviors.std

import io.github.scalaquest.core.model.Direction.Direction
import io.github.scalaquest.core.model.Room
import io.github.scalaquest.core.model.common.Actions.Open
import io.github.scalaquest.core.model.std.StdModel.{
  Door,
  Key,
  Openable,
  RoomLink,
  StdGameState,
  StdPlayer,
  StdState,
  bagLens,
  itemsLens
}
import org.scalatest.wordspec.AnyWordSpec

class RoomLinkTest extends AnyWordSpec {

  "A RoomLinkBehavior" when {
    val startRoom: Room  = Room("startRoom", () => Map[Direction, Room]())
    val targetRoom: Room = Room("targetRoom", () => Map[Direction, Room]())
    val simpleState: StdState = StdState(
      game = StdGameState(
        player = StdPlayer(bag = Set(), location = startRoom),
        ended = false,
        rooms = Set(targetRoom, startRoom),
        itemsInRooms = Map()
      ),
      messages = Seq()
    )

    "a Key is required" when {
      val targetKey: Key              = Key("targetKey")
      val roomLinkBehaviorKeyRequired = RoomLink(targetRoom, Some(Openable(requiredKey = Some(targetKey))))
      val targetPortalWKey            = Door("roomLink", roomLinkBehaviorKeyRequired)

      val stateWithKey: StdState          = bagLens.modify(_ + targetKey)(simpleState)
      val stateWithKeyAndPortal: StdState = itemsLens.modify(_ + (startRoom -> Set(targetPortalWKey)))(stateWithKey)
      val targetLink: StdState => Option[RoomLink] = state => {
        for {
          itemsInLocation <- state.game.itemsInRooms.get(startRoom)
          roomLink        <- itemsInLocation.collectFirst({ case Door(_, roomLink: RoomLink) => roomLink })
        } yield roomLink
      }

      "the user says 'open the door'" should {
        val openDoorWithKeyMaybeReaction = targetPortalWKey.use(Open, stateWithKeyAndPortal, Some(targetKey))

        "let the item open only with the right Key" in {
          val newState =
            openDoorWithKeyMaybeReaction.fold(stateWithKeyAndPortal)(reaction => reaction(stateWithKeyAndPortal))
          val isDoorOpen =
            (for {
              rl <- targetLink(newState)
              op <- rl.openable
            } yield op.isOpen) getOrElse false

          assert(openDoorWithKeyMaybeReaction.isDefined, "a reaction has not been generated")
          assert(isDoorOpen, "The roomLink is not in open state")
        }
        "not open without a Key" in {}
      }
    }

    "a key is not required" when {

      "the user says 'open the door'" should {
        "open only without any Key" in {}
        "not open with any Key" in {}
      }
    }

    "the door is open" when {
      "the user says 'enter the door'" should {
        "move the player in the designed room" in {}
      }

      "the door is closed" when {
        "the user says 'enter the door'" should {
          "not move the player in the designed room" in {}
        }
      }
    }
  }
}
