package io.github.scalaquest.core.dictionary

import io.github.scalaquest.core.dictionary.generators.{Generator, GeneratorK}
import io.github.scalaquest.core.dictionary.verbs.{Verb, VerbPrep}
import io.github.scalaquest.core.model.{Action, ItemRef, Model}
import io.github.scalaquest.core.parsing.scalog.{Clause, Program}

trait DictionaryImplicits {

  object implicits {
    import cats.implicits.catsKernelStdSemilatticeForSet
    import generators.implicits.listGenerator

    implicit def itemToEntryGenerator[I <: Item]: Generator[I, Map[ItemRef, I]] =
      Generator.makeEntry(i => i.ref -> i)

    implicit def verbToEntryGenerator: Generator[Verb, Map[VerbPrep, Action]] =
      Generator.makeEntry(_.binding)

    /*
     * Temporary solution:
     * We had to declare these generators explicitly because Scala compiler
     * couldn't infer them implicitly, using listGenerator, in a similar way it
     * infers the map generators.
     */
    implicit def verbListToProgram: GeneratorK[List, Verb, Program] = listGenerator[Verb, Program]

    implicit def itemListToProgram[I <: Item]: GeneratorK[List, I, Program] =
      listGenerator[I, Program]

    implicit def verbToProgram: Generator[Verb, Program] = Generator.instance(v => Set(v.clause))

    implicit def itemToProgram: Generator[Item, Program] =
      Generator.instance(i => {
        import io.github.scalaquest.core.parsing.scalog.dsl._
        val name      = CompoundBuilder("name").constructor
        val adjective = CompoundBuilder("adjective").constructor

        def names: List[Clause]      = List(i.description.base.name).map(name(_))
        def adjectives: List[Clause] = i.description.decorators.toList.map(_.name).map(adjective(_))

        (names ++ adjectives).toSet
      })
  }
}
