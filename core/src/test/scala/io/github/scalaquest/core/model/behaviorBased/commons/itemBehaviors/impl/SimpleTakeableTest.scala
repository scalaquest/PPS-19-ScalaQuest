package io.github.scalaquest.core.model.behaviorBased.commons.itemBehaviors.impl

import io.github.scalaquest.core.TestsUtils.simpleState
import io.github.scalaquest.core.model.Action.Common.Take
import io.github.scalaquest.core.model.{ItemDescription, ItemRef}
import io.github.scalaquest.core.model.behaviorBased.impl.SimpleModel.{
  SimpleGenericItem,
  SimpleTakeable,
  itemsLens
}

import org.scalatest.wordspec.AnyWordSpec

class SimpleTakeableTest extends AnyWordSpec {
  "A Takeable behavior" when {
    val takeable = SimpleTakeable()

    "applied to an item" when {
      val itemDescription       = ItemDescription("item")
      val targetItem            = SimpleGenericItem(itemDescription, ItemRef(itemDescription), takeable)
      val stateWithTargetInRoom = simpleState.copyWithItemInLocation(targetItem)
      val stateWithoutTargetInRoom =
        itemsLens.modify(_ + (targetItem.ref -> targetItem))(simpleState)

      "the user says 'take the item'" should {
        "let the item disappear from the current room" in {
          for {
            react <- targetItem.use(Take, None)(stateWithTargetInRoom) toRight fail(
              "Reaction not generated"
            )
            modState <- Right(react(stateWithTargetInRoom))
          } yield assert(
            !modState.location.items(modState).contains(targetItem),
            "The item is into the room yet"
          )
        }

        "appear into the bag" in {
          for {
            react <- targetItem.use(Take, None)(stateWithTargetInRoom) toRight fail(
              "Reaction not generated"
            )
            modState <- Right(react(stateWithTargetInRoom))
          } yield assert(
            modState.bag.contains(targetItem),
            "The item is not into the bag"
          )
        }

        "not work if the item is not in the current room" in {
          assert(
            targetItem.use(Take, None)(stateWithoutTargetInRoom).isEmpty,
            "Generated a reaction when it shouldn't"
          )
        }
      }
    }
  }
}
