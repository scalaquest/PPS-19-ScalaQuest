package io.github.scalaquest.core.parsing.engine.exceptions

/**
 * An exception that is thrown if an [[io.github.scalaquest.core.parsing.engine.Engine]]
 * was provided an invalid theory.
 */
case class InvalidTheoryException(line: Int, pos: Int) extends Throwable

object InvalidTheoryException {

  def apply(e: alice.tuprolog.InvalidTheoryException): InvalidTheoryException = InvalidTheoryException(e.line, e.pos)
}
