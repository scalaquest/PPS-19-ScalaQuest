package io.github.scalaquest.core.parsing.engine

import alice.tuprolog.lib.{DCGLibrary => TuPrologDCGLibrary}
import alice.tuprolog.{Library => TuPrologLibrary}

/** Representation of a Prolog library */
abstract class BaseLibrary

/** A tuProlog enabled library */
trait TuProlog { self: BaseLibrary =>
  def toTuProlog: TuPrologLibrary
}

/** Declarative Clause Grammar library */
case object DCGLibrary extends BaseLibrary with TuProlog {
  override def toTuProlog: TuPrologLibrary = new TuPrologDCGLibrary
}
