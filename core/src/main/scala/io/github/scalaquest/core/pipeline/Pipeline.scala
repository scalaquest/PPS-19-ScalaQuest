package io.github.scalaquest.core.pipeline

import io.github.scalaquest.core.model.Model
import io.github.scalaquest.core.pipeline.interpreter.Interpreter
import io.github.scalaquest.core.pipeline.lexer.Lexer
import io.github.scalaquest.core.pipeline.parser.Parser
import io.github.scalaquest.core.pipeline.reducer.Reducer
import io.github.scalaquest.core.pipeline.resolver.Resolver

trait Pipeline[S <: Model#State] {
  def run(rawSentence: String): Either[String, S]
}

object Pipeline {

  def apply[S <: Model#State](
    lexer: Lexer,
    parser: Parser,
    resolver: Resolver,
    interpreterFactory: S => Interpreter[S],
    reducerFactory: S => Reducer[S]
  )(state: S): Pipeline[S] =
    (rawSentence: String) =>
      for {
        lr  <- (lexer tokenize rawSentence) toRight "Couldn't understand input."
        pr  <- (parser parse lr) toRight "Couldn't understand input."
        rr  <- resolver resolve pr
        ir  <- interpreterFactory(state) interpret rr
        rdr <- Right(reducerFactory(state) reduce ir)
      } yield rdr.state
}
