package io.github.scalaquest.core.pipeline.lexer

case class SimpleLexerResult(tokens: Seq[Token]) extends LexerResult

case object SimpleLexer extends Lexer {

  override def tokenize(rawSentence: String): LexerResult =
    SimpleLexerResult((rawSentence.toLowerCase split " ").filter(_ != "").toSeq)
}
