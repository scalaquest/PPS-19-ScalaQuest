package io.github.scalaquest.core.model.behaviorBased.commons.itemBehaviors.impl

import io.github.scalaquest.core.TestsUtils
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import TestsUtils._
import TestsUtils.model._
import io.github.scalaquest.core.model.ItemDescription.dsl.i
import io.github.scalaquest.core.model.behaviorBased.commons.actioning.CActions.Open

class ContainerTest extends AnyWordSpec with Matchers {
  "A Container behavior" when {
    "a key is required" should {
      val containerItem = GenericItem(
        i("Item"),
        Seq(Container.lockedBuilder(items = Set(apple), key = TestsUtils.key))
      )

      val initialState = State(
        actionsMap,
        bag = Set(TestsUtils.key.ref),
        location = targetRoom.ref,
        items = Map(
          appleItemRef      -> apple,
          keyItemRef        -> TestsUtils.key,
          containerItem.ref -> containerItem
        ),
        rooms = Map(targetRoom.ref -> targetRoom.copy(_items = () => Set(containerItem.ref)))
      )

      "not open without the right key" in {
        for {
          react <- containerItem.use(Open, None)(initialState) toRight fail(
            "Reaction not generated"
          )
          msgs <- Right(react(initialState)._2)
        } yield msgs should contain(CMessages.FailedToOpen(containerItem))
      }

      "open and show the items after has been opened" in {
        for {
          react <- containerItem.use(Open, Some(TestsUtils.key))(initialState) toRight fail(
            "Reaction not generated"
          )
          modState  <- Right(react(initialState)._1)
          msgs      <- Right(react(initialState)._2)
          itemInLoc <- Right(modState.location.items(modState))
        } yield {
          msgs should contain(CMessages.ReversedIntoLocation(Set(apple)))
          msgs should contain(CMessages.Opened(containerItem))
          itemInLoc should contain(apple)
        }
      }
    }

    "a key is not required" should {
      val containerItem = GenericItem(
        i("Item"),
        Seq(Container.unlockedBuilder(items = Set(apple)))
      )

      val initialState = State(
        actionsMap,
        location = targetRoom.ref,
        items = Map(
          appleItemRef      -> apple,
          containerItem.ref -> containerItem
        ),
        rooms = Map(targetRoom.ref -> targetRoom.copy(_items = () => Set(containerItem.ref)))
      )

      "open and show the items after has been opened" in {
        for {
          react <- containerItem.use(Open, None)(initialState) toRight fail(
            "Reaction not generated"
          )
          modState  <- Right(react(initialState)._1)
          msgs      <- Right(react(initialState)._2)
          itemInLoc <- Right(modState.location.items(modState))
        } yield {
          msgs should contain(CMessages.ReversedIntoLocation(Set(apple)))
          msgs should contain(CMessages.Opened(containerItem))
          itemInLoc should contain(apple)
        }
      }
    }
  }
}
