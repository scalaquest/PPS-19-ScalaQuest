/*
 * Copyright (c) 2021 ScalaQuest Team.
 *
 * This file is part of ScalaQuest, and is distributed under the terms of the MIT License as
 * described in the file LICENSE in the ScalaQuest distribution's top directory.
 */

package io.github.scalaquest.core.pipeline.parser

import io.github.scalaquest.core.model.{BaseItem, DecoratedItem, ItemDescription}
import io.github.scalaquest.core.parsing.engine.Engine
import io.github.scalaquest.core.parsing.scalog.{Atom, Compound, Term, Variable}
import io.github.scalaquest.core.pipeline.lexer.LexerResult

import scala.annotation.nowarn

case class SimpleParserResult(tree: AbstractSyntaxTree) extends ParserResult

trait Helpers {

  object dsl {
    import io.github.scalaquest.core.parsing.scalog.dsl._

    val X         = Variable("X")
    val imp       = CompoundBuilder("imp").constructor
    val phrase    = CompoundBuilder("phrase").constructor
    val sentence  = CompoundBuilder("sentence").extractor.toTerms
    val `/`       = CompoundBuilder("/").extractor.toStrings
    val decorated = CompoundBuilder("decorated").extractor.toTerms
  }

  object ItemDescription {
    import dsl.decorated

    @nowarn("msg=not all missing cases are reported")
    @nowarn("msg=match.*exhaustive")
    private def toItemDescription(term: Term): ItemDescription =
      term match {
        case Atom(name) => BaseItem(name)
        case decorated(Atom(decoration), t) =>
          DecoratedItem(decoration, toItemDescription(t))
      }

    def unapply(t: Term): Option[ItemDescription] =
      t match {
        case obj @ (_: Compound | _: Term) => Some(toItemDescription(obj))
        case _                             => None
      }

  }

  object Preposition {
    private final val empty = "{}"

    def unapply(prep: String): Option[Option[String]] =
      prep match {
        case `empty` => Some(None)
        case x       => Some(Some(x))
      }
  }

  val i = ItemDescription
  val p = Preposition
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
        case sentence(`/`(verb, p(prep)), Atom(subject)) =>
          Some(AbstractSyntaxTree.Intransitive(verb, prep, subject))
        case sentence(`/`(verb, p(prep)), Atom(subject), i(obj)) =>
          Some(AbstractSyntaxTree.Transitive(verb, prep, subject, obj))
        case sentence(
              `/`(verb, p(prep)),
              Atom(subject),
              i(directObj),
              i(indirectObj)
            ) =>
          Some(AbstractSyntaxTree.Ditransitive(verb, prep, subject, directObj, indirectObj))
        case _ => None
      }
    } yield SimpleParserResult(ast)
  }
}
