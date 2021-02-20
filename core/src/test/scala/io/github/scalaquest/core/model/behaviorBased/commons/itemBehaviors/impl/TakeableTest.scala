package io.github.scalaquest.core.model.behaviorBased.commons.itemBehaviors.impl

import io.github.scalaquest.core.TestsUtils
import io.github.scalaquest.core.model.ItemDescription
import io.github.scalaquest.core.model.behaviorBased.commons.actioning.CommonActions.Take
import org.scalatest.wordspec.AnyWordSpec
import TestsUtils._
import TestsUtils.model._
import org.scalatest.matchers.should.Matchers

class TakeableTest extends AnyWordSpec with Matchers {

  "A Takeable behavior" when {

    "applied to an item" when {
      val targetItem = GenericItem(ItemDescription("item"), Seq(Takeable.builder()))

      val stateWithTargetInRoom = simpleState.copyWithItemInLocation(targetItem)
      val stateWithoutTargetInRoom =
        itemsLens.modify(_ + (targetItem.ref -> targetItem))(simpleState)

      "the user says 'take the item'" should {
        "let the item disappear from the current room" in {
          for {
            react <- targetItem.use(Take, None)(stateWithTargetInRoom) toRight fail(
              "Reaction not generated"
            )
            modState <- Right(react(stateWithTargetInRoom)._1)
            msgs     <- Right(react(stateWithTargetInRoom)._2)
          } yield {
            modState.location.items(modState) should not contain targetItem
            msgs should contain(Messages.Taken(targetItem))
          }
        }

        "appear into the bag" in {
          for {
            react <- targetItem.use(Take, None)(stateWithTargetInRoom) toRight fail(
              "Reaction not generated"
            )
            modState <- Right(react(stateWithTargetInRoom)._1)
          } yield modState.bag should contain(targetItem)

        }

        "not work if the item is not in the current room" in {
          targetItem.use(Take, None)(stateWithoutTargetInRoom) shouldBe None
        }
      }
    }
  }
}
