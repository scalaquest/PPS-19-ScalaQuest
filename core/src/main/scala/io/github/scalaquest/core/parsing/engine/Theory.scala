package io.github.scalaquest.core.parsing.engine

import alice.tuprolog.{Theory => TuPrologTheory}

/** Representation of a Prolog theory. */
abstract class BaseTheory {
  def source: String
}

/** A tuProlog enabled theory. */
trait TuPrologConverter { self: BaseTheory =>
  def toTuProlog: TuPrologTheory = new TuPrologTheory(source)
}

object Theory {

  def apply(source: String): Theory = new SimpleTheory(source) with TuPrologConverter

  class SimpleTheory(val source: String) extends BaseTheory
}
