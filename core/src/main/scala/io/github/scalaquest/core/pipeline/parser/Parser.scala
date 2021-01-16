package io.github.scalaquest.core.pipeline.parser

import io.github.scalaquest.core.pipeline.lexer.LexerResult

sealed trait AST

object AST {
  // https://it.wikipedia.org/wiki/Verbo_ditransitivo
  // Transitive("get", "you", "key")
  final case class Intransitive(verb: String, subject: String)               extends AST
  final case class Transitive(verb: String, subject: String, target: String) extends AST

  final case class Ditransitive(verb: String, subject: String, target1: String, target2: String)
    extends AST
}

trait ParserResult {
  def tree: AST
}

trait Parser {
  def parse(lexerResult: LexerResult): Option[ParserResult]
}
