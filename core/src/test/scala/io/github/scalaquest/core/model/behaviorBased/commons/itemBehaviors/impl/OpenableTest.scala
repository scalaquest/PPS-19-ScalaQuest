package io.github.scalaquest.core.model.behaviorBased.commons.itemBehaviors.impl

import io.github.scalaquest.core.TestsUtils
import io.github.scalaquest.core.model.behaviorBased.commons.actioning.CActions.Open
import io.github.scalaquest.core.model.ItemDescription
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import TestsUtils._
import TestsUtils.model._

class OpenableTest extends AnyWordSpec with Matchers {

  "An Openable behavior" when {
    "a key is required" when {
      "let the item open only with the right Key" in {
        val targetKey           = Key(ItemDescription("key"))
        val (targetItem, state) = initializeDoorAndKey(Some(targetKey))

        for {
          react <- targetItem.use(Open, Some(targetKey))(state) toRight fail(
            "Reaction not generated"
          )
          msgs <- Right(react(state)._2)
        } yield {
          msgs should contain(CMessages.Opened(targetItem))
        }
      }

      "not open without the right Key" in {
        val wrongKey            = Key(ItemDescription("wrongkey"))
        val targetKey           = Key(ItemDescription("key"))
        val (targetItem, state) = initializeDoorAndKey(Some(targetKey))

        for {
          react <- targetItem.use(Open, Some(wrongKey))(state) toRight fail(
            "Reaction not generated"
          )
          msgs <- Right(react(state)._2)
        } yield msgs should contain(CMessages.FailedToOpen(targetItem))
      }

      "say that the door is already opened, if already opened" in {
        val targetKey           = Key(ItemDescription("key"))
        val (targetItem, state) = initializeDoorAndKey(Some(targetKey))

        for {
          openReact <- targetItem.use(Open, Some(targetKey))(state) toRight fail(
            "Reaction not generated"
          )
          openedState <- Right(openReact(state)._1)
          openAgainReact <- targetItem.use(Open, Some(targetKey))(openedState) toRight fail(
            "Reaction not generated"
          )
          msgs <- Right(openAgainReact(openedState)._2)
        } yield msgs should contain(CMessages.AlreadyOpened(targetItem))
      }
    }

    "a key is not required" when {
      val (targetItem, state) = initializeDoorAndKey(None)

      "open without Key" in {
        for {
          react <- targetItem.use(Open, None)(state) toRight fail(
            "Reaction not generated"
          )
          msgs <- Right(react(state)._2)
        } yield msgs should contain(CMessages.Opened(targetItem))

      }
      "not open with any Key" in {
        val wrongKey            = Key(ItemDescription("wrongkey"))
        val (targetItem, state) = initializeDoorAndKey(None)

        for {
          react <- targetItem.use(Open, Some(wrongKey))(state) toRight fail(
            "Reaction not generated"
          )
          msgs <- Right(react(state)._2)
        } yield msgs should contain(CMessages.FailedToOpen(targetItem))
      }
    }
  }

  def initializeDoorAndKey(requiredKey: Option[Key]): (GenericItem, S) = {
    val openable = requiredKey.map(k => Openable.lockedBuilder(requiredKey = k)) getOrElse Openable
      .unlockedBuilder()
    val targetItem = GenericItem(ItemDescription("item"), Seq(openable))
    val state      = simpleState.copyWithItemInLocation(targetItem)

    (targetItem, state)
  }
}
