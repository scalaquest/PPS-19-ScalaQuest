package io.github.scalaquest.core.model.std

import org.scalatest.wordspec.AnyWordSpec

class StdModelTest extends AnyWordSpec {
  "The StdModel" should {

    "expose commonBehaviors interfaces" in {
      assertCompiles("StdModel.CommonBehaviors")
      assertCompiles("val takeable = new StdModel.CommonBehaviors.Takeable {}")
    }

    "expose CommonBehaviors implementations" in {
      assertCompiles("StdModel.Openable()")
      assertCompiles("StdModel.Takeable()")
    }

    "expose commonItems interfaces" in {
      assertCompiles("StdModel.CommonItems")
      assertCompiles("case class ItemX(name: String) extends StdModel.CommonItems.GenericItem")
    }

    "expose CommonItems implementations" in {
      assertCompiles("val item = StdModel.GenericItem(\"item\")")
      assertCompiles("val key = StdModel.Key(\"key\")")
    }

    "expose the standard state implementation" in {
      assertCompiles("val state = StdModel.StdState")
    }
  }
}
