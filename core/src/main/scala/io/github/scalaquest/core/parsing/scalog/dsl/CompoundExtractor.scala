package io.github.scalaquest.core.parsing.scalog.dsl

import io.github.scalaquest.core.parsing.scalog.Term

/**
 * An object that allows to destructure a [[Compound]] in its arguments.
 */
trait CompoundExtractor {

  /** The type of the arguments */
  type Out

  /**
   * Allows to simulate the Prolog compound term during pattern matching.
   * @param term
   *   the [[Compound]] to extract data from
   * @return
   *   the sequence of extracted terms.
   */
  def unapplySeq(term: Term): Option[Seq[Out]]
}
