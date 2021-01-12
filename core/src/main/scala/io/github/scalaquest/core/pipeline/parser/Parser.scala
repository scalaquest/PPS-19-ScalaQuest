package io.github.scalaquest.core.pipeline.parser

import io.github.scalaquest.core.parsing.engine.{Atom, Compound, Engine, Variable}
import io.github.scalaquest.core.pipeline.lexer.LexerResult

/**
 * A parser that takes a sequence of Tokens and optionally returns an
 * abstract syntax tree.
 */
trait Parser {

  /**
   * Performs the syntactic analysis on a sequence of tokens.
   * @param lexerResult the input finite sequence of tokens
   * @return optionally the result of the syntactic analysis
   */
  def parse(lexerResult: LexerResult): Option[ParserResult]
}

/** Result of the syntactic analysis operation. */
trait ParserResult {

  /** The abstract syntax tree obtained. */
  def tree: AbstractSyntaxTree
}

object Parser {
  def apply(engine: Engine): Parser = SimplePrologParser(engine)

  case class SimplePrologParser(engine: Engine) extends PrologParser
}
