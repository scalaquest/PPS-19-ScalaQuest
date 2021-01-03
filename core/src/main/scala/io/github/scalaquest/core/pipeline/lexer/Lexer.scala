package io.github.scalaquest.core.pipeline.lexer

import io.github.scalaquest.core.pipeline.lexer.Lexer.Token

trait LexerResult {
  def tokens: Seq[Token]
}

trait Lexer {
  def tokenize(rawSentence: String): LexerResult
}

object Lexer {
  type Token = String
}

case class SimpleLexerResult(tokens: Seq[Token]) extends LexerResult

case object SimpleLexer extends Lexer {
  override def tokenize(rawSentence: String): LexerResult =
    SimpleLexerResult((rawSentence.toLowerCase split " ").filter(_ != "").toSeq)
}
