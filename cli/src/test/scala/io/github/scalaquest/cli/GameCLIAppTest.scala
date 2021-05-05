/*
 * Copyright (c) 2021 ScalaQuest Team.
 *
 * This file is part of ScalaQuest, and is distributed under the terms of the MIT License as
 * described in the file LICENSE in the ScalaQuest distribution's top directory.
 */

package io.github.scalaquest.cli

import io.github.scalaquest.core.dictionary.verbs.Verb
import io.github.scalaquest.core.model.{Message, MessagePusher}
import io.github.scalaquest.core.model.behaviorBased.simple.SimpleModel
import io.github.scalaquest.core.pipeline.Pipeline
import io.github.scalaquest.core.pipeline.Pipeline.PartialBuilder
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class GameCLIAppTest extends AnyWordSpec with Matchers {
  import CLITestHelper.{model, state => initState}

  val testMessage = new Message {}

  val app: GameCLIApp[SimpleModel.type] = new GameCLIApp(model) {
    override def state: S = initState

    override def pipelineBuilder: PartialBuilder[S, M] =
      _ =>
        new Pipeline[model.type](model) {

          override def run(
            rawSentence: String
          ): Either[String, (SimpleModel.SimpleState, Seq[Message])] =
            Right(state.copy(ended = true) -> Seq(testMessage))
        }

    override def messagePusher: MessagePusher[String] = _ => ""

    override def verbs: Set[Verb] = Set()

    override def items: Set[I] = Set()
  }

  "The game" should {
    "be built using the given model and pipeline" in {
      app.game.send("anything")(initState) shouldBe Right(
        initState.copy(ended = true) -> Seq(testMessage)
      )
    }
  }
}
