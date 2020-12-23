package io.github.scalaquest.core.pipeline.lexer

trait Token {
  def content: String
}

trait LexerResult {
  def tokens: Seq[Token]
}

trait Lexer {
  def tokenize(rawSentence: String): Option[LexerResult]
}

//case object SimpleLexer extends Lexer {
//  override def tokenize(rawSentence: String): Some[LexerResult] = Some((rawSentence split " ").toSeq))
//}
