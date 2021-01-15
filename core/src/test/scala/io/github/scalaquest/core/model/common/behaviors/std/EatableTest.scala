package io.github.scalaquest.core.model.common.behaviors.std

import io.github.scalaquest.core.model.common.Actions.Eat

import io.github.scalaquest.core.model.std.StdModel.{GenericItem, StdState, Takeable, bagLens, Eatable, itemsLens}
import org.scalatest.wordspec.AnyWordSpec

class EatableTest extends AnyWordSpec {
  "A Eatable behavior" when {
    val eatable = Eatable()
    val room    = BehaviorsTestsUtils.startRoom

    "applied to an item" when {
      val item                  = GenericItem("eatableItem", eatable)
      val stateItemInRoom       = itemsLens.modify(_ + (room -> Set(item)))(BehaviorsTestsUtils.simpleState)
      val stateItemInBag        = bagLens.modify(_ + item)(BehaviorsTestsUtils.simpleState)
      val stateNoItem: StdState = BehaviorsTestsUtils.simpleState

      "the user says 'eat the item'" should {
        "let the item disappear if it is in the current room" in {
          for {
            react         <- item.use(Eat, stateItemInRoom, None) toRight fail("Reaction not generated")
            modState      <- Right(react(stateItemInRoom))
            currRoomItems <- modState.game.itemsInRooms.get(room) toRight fail("Error into the test implementation")
          } yield assert(!currRoomItems.contains(item), "The item is into the room yet")
        }

        "let the item disappear if it is in the bag" in {
          for {
            react    <- item.use(Eat, stateItemInBag, None) toRight fail("Reaction not generated")
            modState <- Right(react(stateItemInBag))
          } yield assert(!modState.game.player.bag.contains(item), "The item is into the bag yet")
        }

        "not work if the item is not in the current room or into the bag" in {
          assert(item.use(Eat, stateNoItem, None).isEmpty, "Generated a reaction when it shouldn't")
        }
      }
    }
  }
}
