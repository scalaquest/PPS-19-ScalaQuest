package io.github.scalaquest.core.model.behaviorBased.common.itemBehaviors.impl

import io.github.scalaquest.core.TestsUtils.{simpleState, startRoom}
import io.github.scalaquest.core.model.Action.Common.Eat
import io.github.scalaquest.core.model.{ItemDescription, ItemRef}
import io.github.scalaquest.core.model.behaviorBased.impl.SimpleModel.{
  SimpleEatable,
  SimpleGenericItem,
  matchRoomsLens,
  playerBagLens,
  roomLens,
  itemsLens,
  StateUtils
}
import org.scalatest.wordspec.AnyWordSpec

class SimpleEatableTest extends AnyWordSpec {
  "A Eatable behavior" when {
    val eatable = SimpleEatable()

    "applied to an item" when {
      val targetItem       = SimpleGenericItem(ItemDescription("item"), ItemRef(), eatable)
      val stateWithItem    = itemsLens.modify(_ + targetItem)(simpleState)
      val currRoomWithItem = roomLens.modify(_ + targetItem.id)(startRoom)

      val stateItemInRoom   = matchRoomsLens.modify(_ + currRoomWithItem)(simpleState)
      val stateItemInBag    = playerBagLens.modify(_ + targetItem.id)(simpleState)
      val stateNoItemInRoom = stateWithItem

      "the user says 'eat the item'" should {
        "let the item disappear if it is in the current room" in {
          for {
            react <- targetItem.use(Eat, stateItemInRoom, None) toRight fail(
              "Reaction not generated"
            )
            modState <- Right(react(stateItemInRoom))
          } yield assert(
            !modState.currentRoom.items.contains(targetItem.id),
            "The item is into the room yet"
          )
        }

        "let the item disappear if it is in the bag" in {
          for {
            react <- targetItem.use(Eat, stateItemInBag, None) toRight fail(
              "Reaction not generated"
            )
            modState <- Right(react(stateItemInBag))
          } yield assert(
            !modState.matchState.player.bag.contains(targetItem.id),
            "The item is into the bag yet"
          )
        }

        "not work if the item is not in the current room or into the bag" in {
          assert(
            targetItem.use(Eat, stateNoItemInRoom, None).isEmpty,
            "Generated a reaction when it shouldn't"
          )
        }
      }
    }
  }
}
