package io.github.scalaquest.core.pipeline.interpreter

import io.github.scalaquest.core.model.std.StdModel
import io.github.scalaquest.core.model.std.StdModel.GenericItem

import org.scalatest.wordspec.AnyWordSpec

class ItemRetrieverTest extends AnyWordSpec {

  "An ItemRetriever built from a dictionary" should {

    val targetRef     = new ItemRef {}
    val targetItem    = GenericItem("target")
    val dictionary    = Map(targetRef -> targetItem)
    val itemRetriever = ItemRetriever(StdModel)(dictionary)

    "retrieve an Item given its ItemRef" in {
      targetRef match {
        case itemRetriever(item) if item == targetItem => succeed
        case _                                         => fail("The returned item is not the expected one")
      }
    }

    "not match given a missing ItemRef" in {
      new ItemRef {} match {
        case itemRetriever(_) => fail("The ItemRef match something unexpected")
        case _                => succeed
      }
    }
  }
}
