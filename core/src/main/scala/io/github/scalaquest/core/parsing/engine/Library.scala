package io.github.scalaquest.core.parsing.engine

import alice.tuprolog.{Library => TuPrologLibrary}
import alice.tuprolog.lib.{DCGLibrary => TuPrologDCGLibrary}

sealed trait Library {
  def ref: TuPrologLibrary
}

case object DCGLibrary extends Library {
  override def ref: TuPrologLibrary = new TuPrologDCGLibrary
}
