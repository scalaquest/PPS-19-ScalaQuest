/*
 * Copyright (c) 2021 ScalaQuest Team.
 *
 * This file is part of ScalaQuest, and is distributed under the terms of the MIT License as
 * described in the file LICENSE in the ScalaQuest distribution's top directory.
 */

package io.github.scalaquest.core.pipeline.parser

import io.github.scalaquest.core.parsing.engine.Engine
import io.github.scalaquest.core.pipeline.lexer.LexerResult

/**
 * A parser that takes a sequence of Tokens and optionally returns an abstract syntax tree.
 */
trait Parser {

  /**
   * Performs the syntactic analysis on a sequence of tokens.
   *
   * @param lexerResult
   *   the input finite sequence of tokens
   * @return
   *   optionally the result of the syntactic analysis
   */
  def parse(lexerResult: LexerResult): Option[ParserResult]
}

/** Result of the syntactic analysis operation. */
trait ParserResult {

  /** The abstract syntax tree obtained. */
  def tree: AbstractSyntaxTree
}

/** Factory for creating [[Parser]]. */
object Parser {

  /** Allows to create a [[Parser]] using an [[Engine]]. */
  def apply(engine: Engine): Parser = SimplePrologParser(engine)

  case class SimplePrologParser(engine: Engine) extends PrologParser
}
