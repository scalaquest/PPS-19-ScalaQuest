/*
 * Copyright (c) 2021 ScalaQuest Team.
 *
 * This file is part of ScalaQuest, and is distributed under the terms of the MIT License as
 * described in the file LICENSE in the ScalaQuest distribution's top directory.
 */

package io.github.scalaquest.core.pipeline.interpreter

import io.github.scalaquest.core.TestsUtils.{apple, appleItemRef, refItemDictionary}
import io.github.scalaquest.core.model.ItemRef
import io.github.scalaquest.core.model.behaviorBased.simple.SimpleModel
import org.scalatest.wordspec.AnyWordSpec

class RefToItemTest extends AnyWordSpec {

  "A RefToItem built from a dictionary" should {
    val refToItem = RefToItem(SimpleModel)(refItemDictionary)

    "retrieve an Item given its ItemRef" in {
      appleItemRef match {
        case refToItem(item) if item == apple => succeed
        case _                                => fail("The returned item is not the expected one")
      }
    }

    "not match given a missing ItemRef" in {
      new ItemRef {} match {
        case refToItem(_) => fail("The ItemRef match something unexpected")
        case _            => succeed
      }
    }
  }
}
