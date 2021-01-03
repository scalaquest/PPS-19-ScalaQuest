package io.github.scalaquest.core.pipeline.lexer

import io.github.scalaquest.core.pipeline.lexer.Lexer.Token

/** A lexer that accepts finite sequence of characters and cannot fail. */
trait Lexer {

  /** Performs the lexical analysis on a sequence of tokens.
    * @param rawSentence the input finite sequence of characters
    * @return the result of the lexical analysis operation
    */
  def tokenize(rawSentence: String): LexerResult
}

object Lexer {
  type Token = String
}

/** Result of the lexical analysis operation. */
trait LexerResult {

  /** Finite sequence of tokens. */
  def tokens: Seq[Token]
}

case class SimpleLexerResult(tokens: Seq[Token]) extends LexerResult

case object SimpleLexer extends Lexer {

  override def tokenize(rawSentence: String): LexerResult =
    SimpleLexerResult((rawSentence.toLowerCase split " ").filter(_ != "").toSeq)
}
