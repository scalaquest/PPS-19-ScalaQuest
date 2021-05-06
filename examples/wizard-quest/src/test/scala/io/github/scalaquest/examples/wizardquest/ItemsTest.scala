/*
 * Copyright (c) 2021 ScalaQuest Team.
 *
 * This file is part of ScalaQuest, and is distributed under the terms of the MIT License as
 * described in the file LICENSE in the ScalaQuest distribution's top directory.
 */

package io.github.scalaquest.examples.wizardquest

import io.github.scalaquest.core.model.behaviorBased.commons.actioning.CActions
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import model.{CMessages, locationRoomLens, roomItemsLens}

class ItemsTest extends AnyWordSpec with Matchers {

  "the Basilisk's tooth" should {
    implicit val state: S = App.state

    "appear when you kill the basilisk" in {
      val msgs = Reactions.killTheBasilisk(state)._2
      msgs should contain(Messages.BasiliskKilled)
    }
  }

  "the sortingHat" should {

    "show the sword after looking inside it, with the right conditions" in {
      val msgs = Reactions.showTheSword(App.state)._2
      msgs should contain(Messages.SwordShown)
    }
  }

  "The stone" should {
    "move the basilisk when thrown" in {
      val msgs = Reactions.basiliskMovesBack(App.state)._2
      msgs should contain(Messages.BasiliskMovedToChamber)
    }
  }

  "Ginny" should {
    "does nothing" in {
      Items.ginny.behaviors shouldBe Seq.empty
    }
  }

  "Tom's diary" should {
    "be destroyed with the tooth" in {
      val react: React = Items.tomDiary
        .use(Actions.Attack, Some(Items.basiliskTooth))(App.state)
        .getOrElse(model.Reaction.empty)

      val msgs = react(App.state)._2
      msgs should contain(CMessages.Won)
    }
  }

  "Tom" should {
    "kill you if you try to do anything to him" in {
      val react: React = Items.tom
        .use(Actions.Attack, None)(App.state)
        .getOrElse(model.Reaction.empty)

      val msgs = react(App.state)._2
      msgs should contain(CMessages.Lost)
    }
  }

  "The griffindor sword" should {
    "be takeable" in {
      val state = (locationRoomLens composeLens roomItemsLens).modify(
        _ + Items.gryffindorSword.ref
      )(App.state)

      val react: React = Items.gryffindorSword
        .use(CActions.Take, None)(state)
        .getOrElse(model.Reaction.empty)

      val msgs = react(state)._2
      msgs should contain(CMessages.Taken(Items.gryffindorSword))
    }
  }

  "The basilisk" should {
    "be killed with the sword" in {
      val react: React = Items.basilisk
        .use(Actions.Attack, Some(Items.gryffindorSword))(App.state)
        .getOrElse(model.Reaction.empty)

      val msgs = react(App.state)._2
      msgs should contain(Messages.BasiliskKilled)
    }

    "kill you if you try to do anything else" in {
      val react: React = Items.basilisk
        .use(Actions.Attack, None)(App.state)
        .getOrElse(model.Reaction.empty)

      val msgs = react(App.state)._2
      msgs should contain(CMessages.Lost)
    }
  }
}
