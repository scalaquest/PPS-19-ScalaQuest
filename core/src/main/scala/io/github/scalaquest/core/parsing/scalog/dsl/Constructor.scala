/*
 * Copyright (c) 2021 ScalaQuest Team.
 *
 * This file is part of ScalaQuest, and is distributed under the terms of the MIT License as
 * described in the file LICENSE in the ScalaQuest distribution's top directory.
 */

package io.github.scalaquest.core.parsing.scalog.dsl

import io.github.scalaquest.core.parsing.scalog.{Compound, Term}

/**
 * A `Constructor` allows the generation of compound terms using a Prolog-like syntax that is
 * checked by the scala compiler.
 */
trait Constructor extends CompoundBase {

  /** Accessor for the method `apply`. */
  object constructor {

    /**
     * Allows to simulate the Prolog compound term declaration.
     * @param arg
     *   the first mandatory term
     * @param args
     *   the other optional terms
     * @return
     *   a [[Compound]] with the given arguments.
     */
    def apply(arg: Term, args: Term*): Compound = Compound(functor, arg, args.toList)
  }
}
