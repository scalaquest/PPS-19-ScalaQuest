/*
 * Copyright (c) 2021 ScalaQuest Team.
 *
 * This file is part of ScalaQuest, and is distributed under the terms of the MIT License as
 * described in the file LICENSE in the ScalaQuest distribution's top directory.
 */

package io.github.scalaquest.core.parsing.engine.exceptions

/**
 * An exception that is thrown if an [[io.github.scalaquest.core.parsing.engine.Engine]] was
 * provided an invalid theory.
 */
case class InvalidTheoryException(line: Int, pos: Int) extends Throwable

object InvalidTheoryException {

  def apply(e: alice.tuprolog.InvalidTheoryException): InvalidTheoryException =
    InvalidTheoryException(e.line, e.pos)
}
