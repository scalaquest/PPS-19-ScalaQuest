package io.github.scalaquest.core.parsing.engine

import alice.tuprolog.{Library => TuPrologLibrary}
import alice.tuprolog.lib.{DCGLibrary => TuPrologDCGLibrary}

/** Representation of a Prolog library */
sealed trait BaseLibrary

/** A tuProlog enabled library */
trait TuProlog { self: BaseLibrary =>
  def toTuProlog: TuPrologLibrary
}

/** Declarative Clause Grammar library */
case object DCGLibrary extends BaseLibrary with TuProlog {
  override def toTuProlog: TuPrologLibrary = new TuPrologDCGLibrary
}
