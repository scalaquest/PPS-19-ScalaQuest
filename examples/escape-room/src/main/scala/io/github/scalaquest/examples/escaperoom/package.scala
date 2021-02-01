package io.github.scalaquest.examples

import io.github.scalaquest.core.dictionary.{Dictionary, Program, Verb, VerbPrep}
import io.github.scalaquest.core.dictionary.generators.{Generator, GeneratorK}
import io.github.scalaquest.core.model.ItemDescription.dsl.{d, i}
import io.github.scalaquest.core.model.{Action, ItemRef}
import io.github.scalaquest.core.model.behaviorBased.impl.SimpleModel
import io.github.scalaquest.core.dictionary.generators.implicits.listToMapGenerator
import io.github.scalaquest.core.parsing.scalog.Clause
import io.github.scalaquest.examples.escaperoom.Item

package object escaperoom extends HasGenerators {
  import io.github.scalaquest.core.model.Action.Common._

  val myModel = SimpleModel

  type Model    = myModel.type
  type State    = myModel.S
  type Item     = myModel.I
  type Room     = myModel.RM
  type Reaction = myModel.Reaction

  var itemsSet: Set[Item] = Set(
    Items.redApple,
    Items.apple,
    Items.greenApple
  )

  def dictionary: Dictionary[Item] = MyDictionary.dictionary

  implicit def itemToEntryGenerator: Generator[Item, Map[ItemRef, Item]] =
    Generator.makeEntry(i => i.ref -> i)

  def items: Map[ItemRef, Item] =
    GeneratorK[List, Item, Map[ItemRef, Item]]
      .generate(dictionary.items)

  implicit def verbToBinding: Generator[Verb, Map[VerbPrep, Action]] =
    Generator.makeEntry(_.binding)

  // generated
  def actions: Map[VerbPrep, Action] =
    GeneratorK[List, Verb, Map[VerbPrep, Action]].generate(dictionary.verbs)

}

class HasGenerators {
  import cats.implicits.{catsKernelStdSemilatticeForSet, catsStdInstancesForList}

  object generators {

    implicit def verbListToProgram: GeneratorK[List, Verb, Program] =
      new GeneratorK[List, Verb, Program]

    implicit def itemListToProgram: GeneratorK[List, Item, Program] =
      new GeneratorK[List, Item, Program]

    implicit def verbToProgram: Generator[Verb, Program] = Generator.instance(v => Set(v.clause))

    implicit def itemToProgram: Generator[Item, Program] =
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
  }
}
