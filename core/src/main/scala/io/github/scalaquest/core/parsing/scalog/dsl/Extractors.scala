package io.github.scalaquest.core.parsing.scalog.dsl

import io.github.scalaquest.core.parsing.scalog.{Atom, Compound, Term}

trait Extractors extends CompoundBase {

  object extractor {

    def to[T](format: Format[T]): CompoundExtractor =
      format match {
        case Formats.Strings => toStrings
        case Formats.Terms   => toTerms
      }
  }

  private object toTerms extends CompoundExtractor {
    override type Out = Term

    override def unapplySeq(term: Term): Option[Seq[Term]] =
      term match {
        case Compound(f, t0, terms) if f == functor => Some(t0 +: terms)
        case _                                      => None
      }
  }

  private object toStrings extends CompoundExtractor {

    override type Out = String

    override def unapplySeq(term: Term): Option[Seq[String]] =
      term match {
        case Compound(f, Atom(t0), terms: List[Atom]) if f == functor =>
          Some(t0 +: terms.map(_.name))
        case _ => None
      }
  }
}
