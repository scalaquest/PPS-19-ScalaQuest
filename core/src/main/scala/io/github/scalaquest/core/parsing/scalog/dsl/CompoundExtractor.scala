/*
 * Copyright (c) 2021 ScalaQuest Team.
 *
 * This file is part of ScalaQuest, and is distributed under the terms of the MIT License as
 * described in the file LICENSE in the ScalaQuest distribution's top directory.
 */

package io.github.scalaquest.core.parsing.scalog.dsl

import io.github.scalaquest.core.parsing.scalog.Term

/**
 * An object that allows to destructure a [[Compound]] in its arguments.
 * @tparam T
 *   the type of the returned objects
 */
trait CompoundExtractor[T] {

  /**
   * Allows to simulate the Prolog compound term during pattern matching.
   * @param term
   *   the [[Compound]] to extract data from
   * @return
   *   the sequence of extracted terms.
   */
  def unapplySeq(term: Term): Option[Seq[T]]
}
