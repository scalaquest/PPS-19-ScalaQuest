package io.github.scalaquest.core.pipeline.parser

import alice.tuprolog.Prolog
import io.github.scalaquest.core.pipeline.lexer.LexerResult

sealed trait AST

object AST {
  final case class Intransitive(verb: String, subject: String)            extends AST
  final case class Transitive(verb: String, subject: String, obj: String) extends AST

  // TODO: https://it.wikipedia.org/wiki/Verbo_ditransitivo
  final case class Ditransitive(
    verb: String,
    subject: String,
    directObj: String,
    indirectObj: String
  ) extends AST
}

trait ParserResult {
  def tree: AST
}

case class SimpleParserResult(tree: AST) extends ParserResult

trait Parser {
  def parse(lexerResult: LexerResult): Option[ParserResult]
}

class PrologParser extends Parser {

  val prolog: Prolog                                                 = new Prolog()
  override def parse(lexerResult: LexerResult): Option[ParserResult] = ???
}
