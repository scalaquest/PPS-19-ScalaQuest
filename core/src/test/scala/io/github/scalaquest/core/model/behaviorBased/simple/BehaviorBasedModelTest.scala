package io.github.scalaquest.core.model.behaviorBased.simple

import io.github.scalaquest.core.TestsUtils
import io.github.scalaquest.core.model.behaviorBased.commons.actioning.CommonActions.{
  Go,
  Inspect,
  Open,
  Take
}
import io.github.scalaquest.core.model.{Direction, ItemDescription, ItemRef}
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class BehaviorBasedModelTest extends AnyWordSpec with Matchers {
  import TestsUtils.model._
  import TestsUtils._

  "A BehaviorBasedItem" should {
    val behavior = new ItemBehavior {
      override def triggers: model.ItemTriggers = { case (Take, _, _, _) => Reactions.empty }
    }

    val item = new BehaviorBasedItem {
      override def behaviors: Seq[ItemBehavior] = Seq(behavior)
      override def description: ItemDescription = ItemDescription("item")
      override def ref: ItemRef                 = ItemRef(ItemDescription("item"))
    }

    val stateWithItem = simpleState.copyWithItemInLocation(item)

    "respond to use calls with a Reaction, if a match is found" in {
      assert(item.use(Take, None)(stateWithItem).isDefined, "The reaction has not been generated")
    }

    "Respond with an empty value, if a match is not found" in {
      assert(
        item.use(Open, None)(stateWithItem).isEmpty,
        "A reaction has been generated, when nothing should happen"
      )
    }
  }

  "A BehaviorBasedGround" should {
    val ground = new BehaviorBasedGround {
      override def behaviors: Seq[GroundBehavior] = Seq(Inspectable())
    }

    "respond to use calls with a Reaction, if a match is found" in {
      assert(ground.use(Inspect)(simpleState).isDefined, "The reaction has not been generated")
    }

    "Respond with an empty value, if a match is not found" in {
      assert(
        ground.use(Go(Direction.North))(simpleState).isEmpty,
        "A reaction has been generated, when nothing should happen"
      )
    }
  }

  "An ItemBehavior" should {
    val itemBehavior = new ItemBehavior {}

    "contain ItemTriggers as a PartialFunction" in {
      itemBehavior.triggers shouldBe PartialFunction.empty
    }
  }

  "A GroundBehavior" should {
    val itemBehavior = new GroundBehavior {}

    "contain a Seq of GroundTriggers" in {
      itemBehavior.triggers shouldBe PartialFunction.empty
    }
  }
}
