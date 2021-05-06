/*
 * Copyright (c) 2021 ScalaQuest Team.
 *
 * This file is part of ScalaQuest, and is distributed under the terms of the MIT License as
 * described in the file LICENSE in the ScalaQuest distribution's top directory.
 */

package io.github.scalaquest.core.pipeline.lexer

case class SimpleLexerResult(tokens: Seq[Token]) extends LexerResult

case object SimpleLexer extends Lexer {

  override def tokenize(rawSentence: String): LexerResult =
    SimpleLexerResult((rawSentence.toLowerCase split " ").filter(_ != "").toSeq)
}
