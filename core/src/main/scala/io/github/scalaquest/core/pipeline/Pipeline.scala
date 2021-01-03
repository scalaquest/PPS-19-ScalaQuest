package io.github.scalaquest.core.pipeline

import cats.data.Reader
import io.github.scalaquest.core.model.Model
import io.github.scalaquest.core.pipeline.interpreter.Interpreter
import io.github.scalaquest.core.pipeline.lexer.Lexer
import io.github.scalaquest.core.pipeline.parser.Parser
import io.github.scalaquest.core.pipeline.reducer.Reducer
import io.github.scalaquest.core.pipeline.resolver.Resolver

abstract class Pipeline[M <: Model](val model: M) {
  def run(rawSentence: String): Either[String, model.S]
}

object Pipeline {

  type PipelineBuilder[-S <: Model#State, M <: Model] = S => Pipeline[M]

  def fromModel[M <: Model](implicit model: M) = new PipelineFromModel[M](model)
}

class PipelineFromModel[M <: Model](val model: M) {

  def build(
    lexer: Lexer,
    parser: Parser,
    resolver: Resolver,
    interpreterFactory: model.S => Interpreter[model.type],
    reducerFactory: model.S => Reducer[model.type]
  )(state: model.S): Pipeline[model.type] =
    new Pipeline[model.type](model) {

      override def run(rawSentence: String): Either[String, model.S] =
        for {
          lr  <- (lexer tokenize rawSentence) toRight "Couldn't understand input."
          pr  <- (parser parse lr) toRight "Couldn't understand input."
          rr  <- resolver resolve pr
          ir  <- interpreterFactory(state) interpret rr
          rdr <- Right(reducerFactory(state) reduce ir)
        } yield rdr.state
    }
}
