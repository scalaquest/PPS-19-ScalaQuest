package io.github.scalaquest.core.model.behaviorBased.common.itemBehaviors.impl

import io.github.scalaquest.core.TestsUtils.simpleState
import io.github.scalaquest.core.model.Action.Common.Open
import io.github.scalaquest.core.model.{ItemDescription, ItemRef}
import io.github.scalaquest.core.model.behaviorBased.impl.SimpleModel.{
  Openable,
  SimpleGenericItem,
  SimpleKey,
  SimpleOpenable,
  SimpleState,
  StateUtils
}
import org.scalatest.wordspec.AnyWordSpec

class SimpleOpenableTest extends AnyWordSpec {
  "An Openable behavior" when {

    val targetOpenable: SimpleState => Option[Openable] = state => {
      val itemsRefsFromCurrRoom = state.itemRefsFromRoomRef(state.matchState.player.location)
      val itemsFromCurrRoom     = state.itemsFromRefs(itemsRefsFromCurrRoom)
      itemsFromCurrRoom.collectFirst({ case SimpleGenericItem(_, _, openable: Openable) =>
        openable
      })
    }

    "a key is required" when {
      val targetKey  = SimpleKey(ItemDescription("key"), ItemRef())
      val openable   = SimpleOpenable(requiredKey = Some(targetKey))
      val targetItem = SimpleGenericItem(ItemDescription("item"), ItemRef(), openable)

      val stateWithTarget    = simpleState.copyWithItemInLocation(targetItem)
      val stateWKeyAndTarget = stateWithTarget.copyWithItemInBag(targetKey)

      "the user says 'open the item'" should {

        "let the item open only with the right Key" in {
          for {
            react <- targetItem.use(Open, stateWKeyAndTarget, Some(targetKey)) toRight fail(
              "Reaction not generated"
            )
            modState <- Right(react(stateWKeyAndTarget))
            openable <- targetOpenable(modState) toRight fail("Error into the test implementation")
          } yield assert(openable.isOpen, "The item is not in open state")
        }

        "not open without the right Key" in {
          assert(targetItem.use(Open, stateWKeyAndTarget, None).isEmpty)
          assert(
            targetItem
              .use(
                Open,
                stateWKeyAndTarget,
                Some(SimpleKey(ItemDescription("key"), new ItemRef {}))
              )
              .isEmpty
          )
        }
      }
    }

    "a key is not required" when {
      val openable              = SimpleOpenable()
      val targetItem            = SimpleGenericItem(ItemDescription("item"), ItemRef(), openable)
      val stateWithTargetInRoom = simpleState.copyWithItemInLocation(targetItem)

      "the user says 'open the item'" should {
        "open without Key" in {
          for {
            react <- targetItem.use(Open, stateWithTargetInRoom, None) toRight fail(
              "Reaction not generated"
            )
            modState <- Right(react(stateWithTargetInRoom))
            openable <- targetOpenable(modState) toRight fail("Error into the test implementation")
          } yield assert(openable.isOpen, "The item is not in open state")

        }
        "not open with any Key" in {
          assert(
            targetItem
              .use(Open, stateWithTargetInRoom, Some(SimpleKey(ItemDescription("key"), ItemRef())))
              .isEmpty
          )
        }
      }
    }
  }
}
