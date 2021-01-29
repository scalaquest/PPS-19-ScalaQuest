package io.github.scalaquest.core.pipeline.parser

import io.github.scalaquest.core.model.{BaseItem, DecoratedItem, ItemDescription}
import io.github.scalaquest.core.parsing.engine.Engine
import io.github.scalaquest.core.parsing.scalog.{Atom, Compound, Term, Variable}
import io.github.scalaquest.core.pipeline.lexer.LexerResult

case class SimpleParserResult(tree: AbstractSyntaxTree) extends ParserResult

trait Helpers {

  object dsl {
    import io.github.scalaquest.core.parsing.scalog.dsl._

    val X        = Variable("X")
    val imp      = CompoundBuilder("imp").constructor
    val phrase   = CompoundBuilder("phrase").constructor
    val sentence = CompoundBuilder("sentence").extractor.toTerms
    val `/`      = CompoundBuilder("/").extractor.toStrings
  }

  object itemDescription {

    private def toItemDescription(t: Term): ItemDescription =
      t match {
        case Atom(name) => BaseItem(name)
        case Compound(Atom(description), item, Nil) =>
          DecoratedItem(description, toItemDescription(item))
      }

    def unapplySeq(t: Seq[Term]): Option[Seq[ItemDescription]] =
      t match {
        case (obj @ (_: Compound | _: Term)) :: Nil =>
          Some(Seq(toItemDescription(obj)))
        case (directObj @ (_: Compound | _: Term)) :: (indirectObj @ (_: Compound |
            _: Term)) :: Nil =>
          Some(Seq(toItemDescription(directObj), toItemDescription(indirectObj)))
      }
  }
}

/**
 * A [[Parser]] implementation that takes advantage of an
 * [[io.github.scalaquest.core.parsing.engine.Engine]] in order to perform the syntactical analysis.
 */
abstract class PrologParser extends Parser with Helpers {

  /** The [[io.github.scalaquest.core.parsing.engine.Engine]] used. */
  protected def engine: Engine

  override def parse(lexerResult: LexerResult): Option[ParserResult] = {
    import dsl._
    import io.github.scalaquest.core.parsing.scalog.dsl.seqToListP
    val tokens = lexerResult.tokens.map(Atom)
    val query  = phrase(imp(X), tokens)

    for {
      r <- engine.solve(query).headOption
      x <- r.getVariable(X)
      ast <- x match {
        case sentence(`/`(verb, prep), Atom(subject)) =>
          Some(AbstractSyntaxTree.Intransitive(verb, subject))
        case sentence(`/`(verb, prep), Atom(subject), itemDescription(obj)) =>
          Some(AbstractSyntaxTree.Transitive(verb, subject, obj))
        case sentence(`/`(verb, prep), Atom(subject), itemDescription(directObj, indirectObj)) =>
          Some(AbstractSyntaxTree.Ditransitive(verb, subject, directObj, indirectObj))
        case _ => None
      }
    } yield SimpleParserResult(ast)
  }
}
