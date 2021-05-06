/*
 * Copyright (c) 2021 ScalaQuest Team.
 *
 * This file is part of ScalaQuest, and is distributed under the terms of the MIT License as
 * described in the file LICENSE in the ScalaQuest distribution's top directory.
 */

package io.github.scalaquest.core

import io.github.scalaquest.core.parsing.engine.{DCGLibrary, Engine, Theory}
import io.github.scalaquest.core.pipeline.Pipeline
import io.github.scalaquest.core.pipeline.interpreter.Interpreter
import io.github.scalaquest.core.pipeline.lexer.SimpleLexer
import io.github.scalaquest.core.pipeline.parser.Parser
import io.github.scalaquest.core.pipeline.reducer.Reducer
import io.github.scalaquest.core.pipeline.resolver.Resolver
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class GameTest extends AnyWordSpec with Matchers {
  import TestsUtils._

  "A GameBuilder" should {
    val gameBuilder = Game.builderFrom(model)

    val game = gameBuilder.build(
      Pipeline
        .builderFrom(model)
        .build(
          SimpleLexer,
          Parser(Engine(Theory(""), Set(DCGLibrary))),
          Resolver.builder(model),
          Interpreter.builder(model),
          Reducer.builder(model)
        )
    )

    "create a proper Game" in {
      game.send("")(simpleState) shouldBe Left("Couldn't understand input.")
    }
  }
}
