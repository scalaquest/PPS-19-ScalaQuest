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

        override def run(rawSentence: String): Either[String, model.S] =
          for {
            lr  <- Right(lexer tokenize rawSentence)
            pr  <- (parser parse lr) toRight "Couldn't understand input."
            rr  <- resolver(state) resolve pr
            ir  <- interpreterBuilder(state) interpret rr
            rdr <- Right(reducerBuilder(state) reduce ir)
          } yield rdr.state
      }
  }

}
