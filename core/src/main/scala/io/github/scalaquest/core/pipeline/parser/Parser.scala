package io.github.scalaquest.core.pipeline.parser

import io.github.scalaquest.core.parsing.engine.{Atom, Compound, Engine, Variable}
import io.github.scalaquest.core.pipeline.lexer.LexerResult

sealed trait AST

object AST {
  final case class Intransitive(verb: String, subject: String)            extends AST
  final case class Transitive(verb: String, subject: String, obj: String) extends AST

  // TODO: https://it.wikipedia.org/wiki/Verbo_ditransitivo
  final case class Ditransitive(
    verb: String,
    subject: String,
    directObj: String,
    indirectObj: String
  ) extends AST
}

trait ParserResult {
  def tree: AST
}

case class SimpleParserResult(tree: AST) extends ParserResult

trait Parser {
  def parse(lexerResult: LexerResult): Option[ParserResult]
}

object Parser {
  def apply(engine: Engine): Parser = new SimplePrologParser(engine)

  abstract class PrologParser extends Parser {

    def engine: Engine

    object dsl {
      import io.github.scalaquest.core.parsing.engine.dsl._
      val X      = Variable("X")
      val i      = CompoundBuilder("i")
      val phrase = CompoundBuilder("phrase")
    }

    override def parse(lexerResult: LexerResult): Option[ParserResult] = {
      import io.github.scalaquest.core.parsing.engine.dsl.seqToListP
      import dsl._
      val tokens = lexerResult.tokens.map(Atom)
      val query  = phrase(i(X), tokens)

      for {
        r <- engine.solve(query).headOption
        x <- r.getVariable(X)
        ast <- x match {
          case Compound(Atom(verb), Atom(subject), Nil) =>
            Some(AST.Intransitive(verb, subject))
          case Compound(Atom(verb), Atom(subject), Atom(obj) :: Nil) =>
            Some(AST.Transitive(verb, subject, obj))
          case Compound(Atom(verb), Atom(subject), Atom(directObj) :: Atom(indirectObj) :: Nil) =>
            Some(AST.Ditransitive(verb, subject, directObj, indirectObj))
          case _ => None
        }
      } yield SimpleParserResult(ast)
    }
  }

  class SimplePrologParser(val engine: Engine) extends PrologParser
}
