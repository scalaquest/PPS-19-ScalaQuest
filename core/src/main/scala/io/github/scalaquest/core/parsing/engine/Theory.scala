/*
 * Copyright (c) 2021 ScalaQuest Team.
 *
 * This file is part of ScalaQuest, and is distributed under the terms of the MIT License as
 * described in the file LICENSE in the ScalaQuest distribution's top directory.
 */

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

  /** Creates a theory with the provided source code. */
  def apply(source: String): Theory = new SimpleTheory(source) with TuPrologConverter

  private class SimpleTheory(val source: String) extends BaseTheory
}
