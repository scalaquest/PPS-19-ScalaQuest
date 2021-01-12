package io.github.scalaquest.core.parsing.engine.exceptions

trait InvalidTheoryException extends Throwable {

  def line: Int

  def pos: Int
}

object InvalidTheoryException {

  def apply(e: alice.tuprolog.InvalidTheoryException): InvalidTheoryException =
    new InvalidTheoryException() {
      override def line: Int = e.line

      override def pos: Int = e.pos
    }
}
