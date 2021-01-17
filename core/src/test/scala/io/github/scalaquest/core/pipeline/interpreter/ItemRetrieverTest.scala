package io.github.scalaquest.core.pipeline.interpreter

import io.github.scalaquest.core.model.std.StdModel
import io.github.scalaquest.core.TestsUtils.{refItemDictionary, appleItemRef, apple}

import org.scalatest.wordspec.AnyWordSpec

class ItemRetrieverTest extends AnyWordSpec {

  "An ItemRetriever built from a dictionary" should {
    val itemRetriever = ItemRetriever(StdModel)(refItemDictionary)

    "retrieve an Item given its ItemRef" in {
      appleItemRef match {
        case itemRetriever(item) if item == apple => succeed
        case _                                    => fail("The returned item is not the expected one")
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
