/*
 * Copyright (c) 2021 ScalaQuest Team.
 *
 * This file is part of ScalaQuest, and is distributed under the terms of the MIT License as
 * described in the file LICENSE in the ScalaQuest distribution's top directory.
 */

package io.github.scalaquest.core.pipeline

import io.github.scalaquest.core.model.{Message, Model}
import io.github.scalaquest.core.pipeline.interpreter.Interpreter
import io.github.scalaquest.core.pipeline.lexer.Lexer
import io.github.scalaquest.core.pipeline.parser.Parser
import io.github.scalaquest.core.pipeline.reducer.Reducer
import io.github.scalaquest.core.pipeline.resolver.Resolver

/**
 * A basic game iteration, from a command to a modified state. The concept of Pipeline implicitly
 * comprehends the initial State.
 */
abstract class Pipeline[M <: Model](val model: M) {
  def run(rawSentence: String): Either[String, (model.S, Seq[Message])]
}

/**
 * Contains a default builder for [[Pipeline]], splitting it into its components.
 */
object Pipeline {

  type PartialBuilder[S <: Model#State, M <: Model] = S => Pipeline[M]

  def builderFrom[M <: Model](model: M) = new PipelineBuilder[M](model)

  class PipelineBuilder[M <: Model](val model: M) {

    def build(
      lexer: Lexer,
      parser: Parser,
      resolver: Resolver.Builder[model.S],
      interpreterBuilder: Interpreter.Builder[model.type, model.S, model.Reaction],
      reducerBuilder: Reducer.Builder[model.type, model.S, model.Reaction]
    )(state: model.S): Pipeline[model.type] =
      new Pipeline[model.type](model) {

        override def run(rawSentence: String): Either[String, (model.S, Seq[Message])] =
          for {
            lr  <- Right(lexer tokenize rawSentence)
            pr  <- (parser parse lr) toRight "Couldn't understand input."
            rr  <- resolver(state) resolve pr
            ir  <- interpreterBuilder(state) interpret rr
            rdr <- Right(reducerBuilder(state) reduce ir)
          } yield (rdr.state, rdr.message)
      }
  }

}
