/*
 * Copyright (c) 2021 ScalaQuest Team.
 *
 * This file is part of ScalaQuest, and is distributed under the terms of the MIT License as
 * described in the file LICENSE in the ScalaQuest distribution's top directory.
 */

package io.github.scalaquest.core.model

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class ItemDescriptionTest extends AnyWordSpec with Matchers {
  "The ItemDescription factory" when {
    "called with only the base" should {
      "create a base item" in {
        ItemDescription("key") shouldBe BaseItem("key")
      }
    }
    "called with an argument" should {
      "create a decorated item" in {
        ItemDescription("key", "little") shouldBe DecoratedItem("little", BaseItem("key"))
      }
    }
    "called with more than one decorator" should {
      "create a nested decorated item" in {
        val rock = ItemDescription("rock", "little", "brown", "slippery")
        rock shouldBe DecoratedItem(
          "little",
          DecoratedItem("brown", DecoratedItem("slippery", BaseItem("rock")))
        )
      }
    }
  }
  "Item description dsl" when {
    import ItemDescription.dsl._
    val apple                  = i("apple")
    val redApple               = i(d("red"), "apple")
    val brownSmallSlipperyRock = i(d("brown", "small", "slippery"), "rock")
    "provided only the base item" should {
      "create a base item" in {
        apple shouldBe BaseItem("apple")
      }
    }
    "provided a decorator" should {
      "create a decorated item" in {
        redApple shouldBe DecoratedItem("red", BaseItem("apple"))
      }
    }
    "provided multiple decorators" should {
      "create a nested decorated item" in {
        brownSmallSlipperyRock shouldBe DecoratedItem(
          "brown",
          DecoratedItem("small", DecoratedItem("slippery", BaseItem("rock")))
        )
      }
    }
  }
  "The base of an item description" when {
    import ItemDescription.base
    "called with a base item" should {
      "return the item itself" in {
        base(BaseItem("key")) shouldBe BaseItem("key")
      }
    }
    "called with a decorated item" should {
      "return the inner base item" in {
        val decoratedItem = DecoratedItem("red", BaseItem("key"))
        base(decoratedItem) shouldBe BaseItem("key")
      }
    }
  }
  "The decorators of an item description" when {
    import ItemDescription.decorators
    "called with a base item" should {
      "return an empty set" in {
        decorators(BaseItem("key")) shouldBe Set()
      }
    }
    "called with a decorated item" should {
      "return a singlet set" in {
        val decoratedItem = DecoratedItem("red", BaseItem("key"))
        decorators(decoratedItem) shouldBe Set("red")
      }
    }
    "called with a nested decorated item" should {
      "return the set of the decorators" in {
        val decoratedItem = DecoratedItem("golden", DecoratedItem("little", BaseItem("key")))
        decorators(decoratedItem) shouldBe Set("golden", "little")
      }
    }
  }
  "The subset function" when {
    import ItemDescription.isSubset
    "the first item is a base item" should {
      val first = BaseItem("key")
      "return true if the second is the same base item" in {
        isSubset(first, BaseItem("key")) shouldBe true
      }
      "return false if the second is another base item" in {
        isSubset(first, BaseItem("rock")) shouldBe false
      }
      "return true if the second is a decorated item with the same base" in {
        val second = DecoratedItem("little", BaseItem("key"))
        isSubset(first, second) shouldBe true
      }
      "return false if the second is a decorated item with a different base" in {
        val second = DecoratedItem("little", BaseItem("rock"))
        isSubset(first, second) shouldBe false
      }
      "return true if the second is a nested decorated item with the same base" in {
        val second = DecoratedItem("little", DecoratedItem("golden", BaseItem("key")))
        isSubset(first, second) shouldBe true
      }
    }
    "the first item is a decorated item" should {
      val first = DecoratedItem("golden", BaseItem("key"))
      "return true if the second item has the same decorators and base" in {
        val second = DecoratedItem("golden", BaseItem("key"))
        isSubset(first, second) shouldBe true
      }
      "return true if the second item has more than one decorator" in {
        val second = DecoratedItem("golden", DecoratedItem("little", BaseItem("key")))
        isSubset(first, second) shouldBe true
      }
      "return false if the second item has a different base" in {
        val second = DecoratedItem("golden", BaseItem("doorknob"))
        isSubset(first, second) shouldBe false
      }
      "return false if the second item doesn't contain the same decorators" in {
        val second = DecoratedItem("silver", BaseItem("key"))
        isSubset(first, second) shouldBe false
      }
    }
    "the first item is a nested decorated item" should {
      val mediumSized = "medium-sized"
      val first       = DecoratedItem("golden", DecoratedItem(mediumSized, BaseItem("key")))
      "return false if the second doesn't contain the same decorators" in {
        val second = DecoratedItem("golden", BaseItem("key"))
        isSubset(first, second) shouldBe false
      }
      "return true if the second contains the same decorators" in {
        val second = DecoratedItem("golden", DecoratedItem(mediumSized, BaseItem("key")))
        isSubset(first, second) shouldBe true
      }
      "return true if the second contains the same decorators in a different order" in {
        val second = DecoratedItem(mediumSized, DecoratedItem("golden", BaseItem("key")))
        isSubset(first, second) shouldBe true
      }
    }
  }
}
