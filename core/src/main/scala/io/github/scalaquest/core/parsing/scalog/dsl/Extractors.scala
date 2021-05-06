/*
 * Copyright (c) 2021 ScalaQuest Team.
 *
 * This file is part of ScalaQuest, and is distributed under the terms of the MIT License as
 * described in the file LICENSE in the ScalaQuest distribution's top directory.
 */

package io.github.scalaquest.core.parsing.scalog.dsl

import io.github.scalaquest.core.parsing.scalog.{Atom, Compound, Term}

import scala.annotation.nowarn

trait Extractors extends CompoundBase {

  object extractor {

    object toTerms extends CompoundExtractor[Term] {

      override def unapplySeq(term: Term): Option[Seq[Term]] =
        term match {
          case Compound(f, t0, terms) if f == functor => Some(t0 +: terms)
          case _                                      => None
        }
    }

    object toStrings extends CompoundExtractor[String] {

      @nowarn("msg=unchecked.*erasure")
      override def unapplySeq(term: Term): Option[Seq[String]] =
        term match {
          case Compound(f, Atom(t0), terms: List[Atom]) if f == functor =>
            Some(t0 +: terms.map(_.name))
          case _ => None
        }
    }
  }
}
