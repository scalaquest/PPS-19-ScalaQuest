/*
 * Copyright (c) 2021 ScalaQuest Team.
 *
 * This file is part of ScalaQuest, and is distributed under the terms of the MIT License as
 * described in the file LICENSE in the ScalaQuest distribution's top directory.
 */

package io.github.scalaquest.core.application

import io.github.scalaquest.core.model.{Action, RoomRef}
import io.github.scalaquest.core.model.behaviorBased.simple.SimpleModel
import io.github.scalaquest.core.pipeline.interpreter.{Interpreter, InterpreterResult}
import io.github.scalaquest.core.pipeline.lexer.{Lexer, SimpleLexer}
import io.github.scalaquest.core.pipeline.parser.{AbstractSyntaxTree, Parser, SimpleParserResult}
import io.github.scalaquest.core.pipeline.reducer.{Reducer, ReducerResult}
import io.github.scalaquest.core.pipeline.resolver.{Resolver, ResolverResult, Statement}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class PipelineProviderTest extends AnyWordSpec with Matchers {

  def state: SimpleModel.S =
    SimpleModel.State(
      actions = Map.empty,
      rooms = Map.empty,
      items = Map.empty,
      location = RoomRef("1")
    )

  val testAction = new Action {}

  val pipelineProvider = new PipelineProvider[SimpleModel.type] {

    override val model: SimpleModel.type = SimpleModel

    override def lexer: Lexer = SimpleLexer

    override def parser: Parser =
      _ => Some(SimpleParserResult(AbstractSyntaxTree.Intransitive("eat", None, "you")))

    override def resolver: Resolver.Builder[S] =
      _ => _ => Right(ResolverResult(Statement.Intransitive(testAction)))

    override def interpreter: Interpreter.Builder[M, S, React] =
      _ => _ => Right(InterpreterResult(model)(s => s.copy(ended = true) -> Seq()))

    override def reducer: Reducer.Builder[M, S, React] =
      s =>
        interpreterResult => {
          val (state, messages) = interpreterResult.reaction(s)
          ReducerResult(model)(state, messages)
        }
  }

  "Pipeline provider" should {
    "pass the given theory to the pipeline" in {
      pipelineProvider.makePipeline(state).run("some string") shouldBe Right(
        (state.copy(ended = true), Seq())
      )
    }
  }
}
