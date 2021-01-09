package io.github.scalaquest.core.pipeline.parser

import io.github.scalaquest.core.parsing.engine
import io.github.scalaquest.core.parsing.engine.Theory.Theory
import io.github.scalaquest.core.parsing.engine.{Atom, Compound, DCGLibrary, Engine, Library, ListP, Variable}
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
  def apply(theory: Theory, libraries: Set[Library] = Set()): Parser = new PrologParser(theory, libraries)

  class PrologParser(val theory: Theory, val libraries: Set[Library]) extends Parser {

    val engine: Engine = Engine(
      theory,
      Set(DCGLibrary)
    )

    object variables {
      import io.github.scalaquest.core.parsing.engine.ops._
      val X      = Variable("X")
      val i      = CompoundBuilder("i")
      val phrase = CompoundBuilder("phrase")
    }

    override def parse(lexerResult: LexerResult): Option[ParserResult] = {
      import io.github.scalaquest.core.parsing.engine.ops.seqToListP
      import variables._
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
}
