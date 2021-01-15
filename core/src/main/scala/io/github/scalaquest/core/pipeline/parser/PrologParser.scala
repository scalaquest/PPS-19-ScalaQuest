package io.github.scalaquest.core.pipeline.parser

import io.github.scalaquest.core.parsing.engine.Engine
import io.github.scalaquest.core.parsing.scalog.{Atom, Compound, Variable}
import io.github.scalaquest.core.pipeline.lexer.LexerResult

case class SimpleParserResult(tree: AbstractSyntaxTree) extends ParserResult

/**
 * A [[Parser]] implementation that takes advantage of an [[io.github.scalaquest.core.parsing.engine.Engine]] in order
 * to perform the syntactical analysis.
 */
abstract class PrologParser extends Parser {

  /** The [[io.github.scalaquest.core.parsing.engine.Engine]] used. */
  protected def engine: Engine

  object dsl {
    import io.github.scalaquest.core.parsing.scalog.dsl._
    val X      = Variable("X")
    val i      = CompoundBuilder("i")
    val phrase = CompoundBuilder("phrase")
  }

  override def parse(lexerResult: LexerResult): Option[ParserResult] = {
    import io.github.scalaquest.core.parsing.scalog.dsl.seqToListP
    import dsl._
    val tokens = lexerResult.tokens.map(Atom)
    val query  = phrase(i(X), tokens)

    for {
      r <- engine.solve(query).headOption
      x <- r.getVariable(X)
      ast <- x match {
        case Compound(Atom(verb), Atom(subject), Nil) =>
          Some(AbstractSyntaxTree.Intransitive(verb, subject))
        case Compound(Atom(verb), Atom(subject), Atom(obj) :: Nil) =>
          Some(AbstractSyntaxTree.Transitive(verb, subject, obj))
        case Compound(Atom(verb), Atom(subject), Atom(directObj) :: Atom(indirectObj) :: Nil) =>
          Some(AbstractSyntaxTree.Ditransitive(verb, subject, directObj, indirectObj))
        case _ => None
      }
    } yield SimpleParserResult(ast)
  }
}
