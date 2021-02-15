package io.github.scalaquest.core.dictionary

import cats.Monoid
import io.github.scalaquest.core.dictionary.generators.{Generator, GeneratorK}
import io.github.scalaquest.core.dictionary.verbs.{Verb, VerbPrep}
import io.github.scalaquest.core.model.{Action, ItemRef, Model}
import io.github.scalaquest.core.parsing.scalog.{Clause, Program}

/**
 * Mixin useful to inject some type class instances in an object.
 */
trait DictionaryImplicits {

  object implicits {

    /** Generator from a `Verb` to an entry `(VerbPrep, Action)`. */
    implicit def verbToEntryGenerator: Generator[Verb, Map[VerbPrep, Action]] =
      Generator.makeEntry(_.binding)

    /** Generator from an `Item` to an entry `(ItemRef, Item)`. */
    implicit def itemToEntryGenerator[I <: Item]: Generator[I, Map[ItemRef, I]] =
      Generator.makeEntry(i => i.ref -> i)

    /** Generator from a `Verb` to a `Program`. */
    implicit def verbToProgram: Generator[Verb, Program] = Generator.instance(v => Set(v.clause))

    /** Generator from an `Item` to a `Program`. */
    implicit def itemToProgram: Generator[Item, Program] =
      Generator.instance(i => {
        import io.github.scalaquest.core.parsing.scalog.dsl._
        val name      = CompoundBuilder("name").constructor
        val adjective = CompoundBuilder("adjective").constructor

        def names: List[Clause]      = List(i.description.base.name).map(name(_))
        def adjectives: List[Clause] = i.description.decorators.toList.map(_.name).map(adjective(_))

        (names ++ adjectives).toSet
      })

    /** Monoid instance for a `Program`. */
    implicit def programMonoid: Monoid[Program] = Monoid.instance(Set(), _ ++ _)
  }
}
