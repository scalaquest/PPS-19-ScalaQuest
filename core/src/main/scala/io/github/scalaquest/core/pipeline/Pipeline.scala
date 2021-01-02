package io.github.scalaquest.core.pipeline

import io.github.scalaquest.core.model.Model
import io.github.scalaquest.core.pipeline.interpreter.Interpreter
import io.github.scalaquest.core.pipeline.lexer.Lexer
import io.github.scalaquest.core.pipeline.parser.Parser
import io.github.scalaquest.core.pipeline.reducer.Reducer
import io.github.scalaquest.core.pipeline.resolver.Resolver

trait Pipeline[M <: Model] {
  def run(rawSentence: String): Either[String, M#S]
}

class PipelineBuilder[M <: Model](implicit val model: M) {

  def build(
    lexer: Lexer,
    parser: Parser,
    resolver: Resolver,
    interpreterFactory: model.S => Interpreter[model.type],
    reducerFactory: model.S => Reducer[model.type]
  )(state: model.S): Pipeline[model.type] =
    (rawSentence: String) =>
      for {
        lr  <- (lexer tokenize rawSentence) toRight "Couldn't understand input."
        pr  <- (parser parse lr) toRight "Couldn't understand input."
        rr  <- resolver resolve pr
        ir  <- interpreterFactory(state) interpret rr
        rdr <- Right(reducerFactory(state) reduce ir)
      } yield rdr.state
}

/*
object Pipeline {

  def apply[M <: Model](
    lexer: Lexer,
    parser: Parser,
    resolver: Resolver,
    interpreterFactory: M#S => Interpreter[M],
    reducerFactory: M#S => Reducer[M]
  )(state: M#S): Pipeline[M#S] =
    (rawSentence: String) =>
      for {
        lr  <- (lexer tokenize rawSentence) toRight "Couldn't understand input."
        pr  <- (parser parse lr) toRight "Couldn't understand input."
        rr  <- resolver resolve pr
        ir  <- interpreterFactory(state) interpret rr
        rdr <- Right(reducerFactory(state) reduce ir)
      } yield rdr.state
}
 */
