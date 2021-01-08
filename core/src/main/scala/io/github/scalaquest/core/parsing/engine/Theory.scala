package io.github.scalaquest.core.parsing.engine

import alice.tuprolog.{Theory => TuPrologTheory}

abstract class BaseTheory {
  def source: String
}

trait TuPrologConverter { self: BaseTheory =>
  def toTuProlog: TuPrologTheory = new TuPrologTheory(source)
}

object Theory {

  type Theory = BaseTheory with TuPrologConverter

  def apply(source: String): Theory = new SimpleTheory(source) with TuPrologConverter

  class SimpleTheory(val source: String) extends BaseTheory
}
