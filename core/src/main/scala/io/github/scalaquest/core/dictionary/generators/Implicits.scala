package io.github.scalaquest.core.dictionary.generators

import cats.implicits.{catsKernelStdSemilatticeForSet, catsStdInstancesForList}
import io.github.scalaquest.core.dictionary.{Program, Verb, VerbC}
import io.github.scalaquest.core.parsing.scalog.Clause

abstract class Implicits {

  object implicits {

    implicit def verbGenerator: Generator[VerbC, Program] = (v: VerbC) => Set(v.clause)

    implicit def itemGenerator: Generator[Item, Program] =
      new Generator[Item, Program] {

        import io.github.scalaquest.core.parsing.scalog.dsl._

        val name      = CompoundBuilder("name").constructor
        val adjective = CompoundBuilder("adjective").constructor

        override def generate(item: Item): Program = {
          def names: List[Clause] = List(item.description.base.name).map(name(_))
          def adjectives: List[Clause] =
            item.description.decorators.toList.map(_.name).map(adjective(_))

          (names ++ adjectives).toSet
        }
      }

    implicit def verbListGenerator: GeneratorK[List, VerbC, Program] =
      new GeneratorK[List, VerbC, Program]

    implicit def itemListGenerator: GeneratorK[List, Item, Program] =
      new GeneratorK[List, Item, Program]
  }
}
