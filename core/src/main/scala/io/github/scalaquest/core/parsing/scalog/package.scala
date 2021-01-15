package io.github.scalaquest.core.parsing

import scalog._

/**
 * This package provides data structures to manage the manipulation of simple
 * Prolog programs. It allows to create terms such as atoms, numbers or
 * compound terms. Those structures are provided with methods that enable the
 * combination of terms to form rules or definite clause grammar rules.
 *
 * ==Overview==
 * A simple [[Term]] can be created by using one of its constructor, such as
 * [[Atom]].
 * {{{
 *   val hello = Atom("hello")
 * }}}
 *
 * A [[Compound]] can be created by combining two or more terms.
 * {{{
 *   val compound = Compound(hello, Atom("world"))
 * }}}
 *
 * This can be used to create a fact and generate the corresponding source code.
 * {{{
 *   val fact = Fact(compound).generate
 * }}}
 *
 * Should result in the following being printed:
 * {{{
 *   hello(world).
 * }}}
 */
package object scalog
