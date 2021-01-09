package io.github.scalaquest.core.pipeline.parser

import io.github.scalaquest.core.parsing.engine.{DCGLibrary, Engine, Theory}
import io.github.scalaquest.core.pipeline.lexer.SimpleLexerResult
import io.github.scalaquest.core.pipeline.parser.Parser.PrologParser
import org.scalatest.wordspec.AnyWordSpec

import scala.io.Source

class PrologParserTest extends AnyWordSpec {

  val source = Source.fromResource("base.pl").mkString +
    """
      |
      |name(key) --> [key].
      |name(door) --> [door].
      |name(apple) --> [apple].
      |name(bag) --> [bag].
      |
      |iv(X^inspect(X)) --> [inspect].
      |tv(X^Y^take(Y,X)) --> [take].
      |tv(X^Y^pick_up(Y,X)) --> [pick,up].
      |v(3/with, Z^Y^X^open(X,Y,Z)) --> [open].
      |v(3/in, Z^Y^X^put(X,Y,Z)) --> [put].
      |""".stripMargin
  val parser: Parser = Parser(Engine(Theory(source), Set(DCGLibrary)))

  "A parser" when {
    "provided an empty sequence of tokens" should {
      "return an empty value" in {
        assert(parser.parse(SimpleLexerResult(Seq())).isEmpty)
      }
    }
    "provided an intransitive sentence" should {
      "return an intransitive ast" in {
        assert(
          parser
            .parse(SimpleLexerResult(Seq("inspect")))
            .map(_.tree)
            .contains(AST.Intransitive("inspect", "you"))
        )
      }
    }
    "provided a transitive sentence" should {
      "return a transitive ast" in {
        assert(
          parser
            .parse(SimpleLexerResult(Seq("take", "the", "key")))
            .map(_.tree)
            .contains(AST.Transitive("take", "you", "key"))
        )
      }
      "recognize phrasal verbs and escape space with underscore" in {
        assert(
          parser
            .parse(SimpleLexerResult(Seq("pick", "up", "the", "key")))
            .map(_.tree)
            .contains(AST.Transitive("pick_up", "you", "key"))
        )
      }
    }
    "provided a ditransitive sentence" should {
      "return a ditransitive ast" in {
        assert(
          parser
            .parse(SimpleLexerResult(Seq("open", "the", "door", "with", "the", "key")))
            .map(_.tree)
            .contains(AST.Ditransitive("open", "you", "door", "key"))
        )
      }
      "distinguish between direct and indirect objects" in {
        assert(
          parser
            .parse(SimpleLexerResult(Seq("put", "the", "apple", "in", "the", "bag")))
            .map(_.tree)
            .contains(AST.Ditransitive("put", "you", "apple", "bag"))
        )
      }
    }
  }
}
