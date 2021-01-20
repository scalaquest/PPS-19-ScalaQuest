package io.github.scalaquest.core.pipeline.lexer

/** A lexer that accepts finite sequence of characters and cannot fail. */
trait Lexer {

  /**
   * Performs the lexical analysis on a sequence of characters.
   * @param rawSentence
   *   the input finite sequence of characters
   * @return
   *   the result of the lexical analysis operation
   */
  def tokenize(rawSentence: String): LexerResult
}

/** Result of the lexical analysis operation. */
trait LexerResult {

  /** A finite sequence of tokens. */
  def tokens: Seq[Token]
}
