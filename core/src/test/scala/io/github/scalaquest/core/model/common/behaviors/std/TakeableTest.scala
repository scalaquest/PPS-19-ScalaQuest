package io.github.scalaquest.core.model.common.behaviors.std

import io.github.scalaquest.core.TestsUtils.{startRoom, simpleState}
import io.github.scalaquest.core.model.common.Actions.Take
import io.github.scalaquest.core.model.std.StdModel.{
  GenericItem,
  StdState,
  Takeable,
  bagLens,
  itemsLens
}
import org.scalatest.wordspec.AnyWordSpec

class TakeableTest extends AnyWordSpec {
  "A Takeable behavior" when {
    val takeable = Takeable()

    "applied to an item" when {
      val item = GenericItem("takeableItem", takeable)
      val stateItemInRoom =
        itemsLens.modify(_ + (startRoom -> Set(item)))(simpleState)
      val stateItemNotInRoom: StdState = simpleState

      "the user says 'take the item'" should {
        "let the item disappear from the current room" in {
          for {
            react    <- item.use(Take, stateItemInRoom, None) toRight fail("Reaction not generated")
            modState <- Right(react(stateItemInRoom))
            currRoomItems <- modState.game.itemsInRooms.get(startRoom) toRight fail(
              "Error into the test implementation"
            )
          } yield assert(!currRoomItems.contains(item), "The item is into the room yet")
        }

        "appear into the bag" in {
          for {
            react    <- item.use(Take, stateItemInRoom, None) toRight fail("Reaction not generated")
            modState <- Right(react(stateItemInRoom))
          } yield assert(modState.game.player.bag.contains(item), "The item is not into the bag")
        }

        "not work if the item is not in the current room" in {
          assert(
            item.use(Take, stateItemNotInRoom, None).isEmpty,
            "Generated a reaction when it shouldn't"
          )
        }
      }
    }
  }
}
