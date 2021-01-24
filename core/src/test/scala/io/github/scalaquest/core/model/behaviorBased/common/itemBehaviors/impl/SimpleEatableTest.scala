package io.github.scalaquest.core.model.behaviorBased.common.itemBehaviors.impl

import io.github.scalaquest.core.TestsUtils.{simpleState, startRoom}
import io.github.scalaquest.core.model.Action.Common.Eat
import io.github.scalaquest.core.model.ItemRef
import io.github.scalaquest.core.model.behaviorBased.impl.SimpleModel.{
  SimpleEatable,
  SimpleGenericItem,
  SimpleState,
  geographyLens,
  playerBagLens
}
import io.github.scalaquest.core.pipeline.parser.ItemDescription
import org.scalatest.wordspec.AnyWordSpec

class SimpleEatableTest extends AnyWordSpec {
  "A Eatable behavior" when {
    val eatable = SimpleEatable()
    val room    = startRoom

    "applied to an item" when {
      val item = SimpleGenericItem(ItemDescription("item"), new ItemRef {}, eatable)
      val stateItemInRoom =
        geographyLens.modify(_ + (room -> Set(item)))(simpleState)
      val stateItemInBag           = playerBagLens.modify(_ + item)(simpleState)
      val stateNoItem: SimpleState = simpleState

      "the user says 'eat the item'" should {
        "let the item disappear if it is in the current room" in {
          for {
            react    <- item.use(Eat, stateItemInRoom, None) toRight fail("Reaction not generated")
            modState <- Right(react(stateItemInRoom))
            currRoomItems <- modState.matchState.geography.get(room) toRight fail(
              "Error into the test implementation"
            )
          } yield assert(!currRoomItems.contains(item), "The item is into the room yet")
        }

        "let the item disappear if it is in the bag" in {
          for {
            react    <- item.use(Eat, stateItemInBag, None) toRight fail("Reaction not generated")
            modState <- Right(react(stateItemInBag))
          } yield assert(
            !modState.matchState.player.bag.contains(item),
            "The item is into the bag yet"
          )
        }

        "not work if the item is not in the current room or into the bag" in {
          assert(item.use(Eat, stateNoItem, None).isEmpty, "Generated a reaction when it shouldn't")
        }
      }
    }
  }
}
