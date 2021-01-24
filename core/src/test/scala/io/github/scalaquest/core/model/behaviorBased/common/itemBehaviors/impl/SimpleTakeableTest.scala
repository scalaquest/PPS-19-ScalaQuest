package io.github.scalaquest.core.model.behaviorBased.common.itemBehaviors.impl

import io.github.scalaquest.core.TestsUtils.{simpleState, startRoom}
import io.github.scalaquest.core.model.Action.Common.Take
import io.github.scalaquest.core.model.ItemRef
import io.github.scalaquest.core.model.behaviorBased.impl.SimpleModel.{
  SimpleGenericItem,
  SimpleState,
  SimpleTakeable,
  geographyLens,
  playerBagLens
}
import io.github.scalaquest.core.pipeline.parser.ItemDescription
import org.scalatest.wordspec.AnyWordSpec

class SimpleTakeableTest extends AnyWordSpec {
  "A Takeable behavior" when {
    val takeable = SimpleTakeable()

    "applied to an item" when {
      val item = SimpleGenericItem(ItemDescription("item"), new ItemRef {}, takeable)
      val stateItemInRoom =
        geographyLens.modify(_ + (startRoom -> Set(item)))(simpleState)
      val stateItemNotInRoom: SimpleState = simpleState

      "the user says 'take the item'" should {
        "let the item disappear from the current room" in {
          for {
            react    <- item.use(Take, stateItemInRoom, None) toRight fail("Reaction not generated")
            modState <- Right(react(stateItemInRoom))
            currRoomItems <- modState.matchState.geography.get(startRoom) toRight fail(
              "Error into the test implementation"
            )
          } yield assert(!currRoomItems.contains(item), "The item is into the room yet")
        }

        "appear into the bag" in {
          for {
            react    <- item.use(Take, stateItemInRoom, None) toRight fail("Reaction not generated")
            modState <- Right(react(stateItemInRoom))
          } yield assert(
            modState.matchState.player.bag.contains(item),
            "The item is not into the bag"
          )
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
