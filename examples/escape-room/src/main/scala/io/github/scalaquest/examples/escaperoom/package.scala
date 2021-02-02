package io.github.scalaquest.examples

import cats.implicits.catsKernelStdSemilatticeForSet
import io.github.scalaquest.core.dictionary.{Dictionary, Verb, VerbPrep}
import io.github.scalaquest.core.dictionary.generators.{Generator, GeneratorK}
import io.github.scalaquest.core.model.{Action, ItemRef}
import io.github.scalaquest.core.model.behaviorBased.impl.SimpleModel
import io.github.scalaquest.core.dictionary.generators.implicits.{listGenerator, listToMapGenerator}
import io.github.scalaquest.core.parsing.scalog.{Clause, Program}
import io.github.scalaquest.examples.escaperoom.Item

package object escaperoom extends HasGenerators {
  import io.github.scalaquest.core.model.Action.Common._
  import generators._

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

  def items: Map[ItemRef, Item] =
    GeneratorK[List, Item, Map[ItemRef, Item]]
      .generate(dictionary.items)

  // generated
  def actions: Map[VerbPrep, Action] =
    GeneratorK[List, Verb, Map[VerbPrep, Action]].generate(dictionary.verbs)

}

trait HasGenerators {

  object generators {

    implicit def itemToEntryGenerator: Generator[Item, Map[ItemRef, Item]] =
      Generator.makeEntry(i => i.ref -> i)

    implicit def verbToBinding: Generator[Verb, Map[VerbPrep, Action]] =
      Generator.makeEntry(_.binding)

    /*
     * Temporary solution:
     * We had to declare these generators explicitly because Scala compiler
     * couldn't infer them implicitly, using listGenerator, in a similar way it
     * infers the map generators.
     */
    implicit val verbListToProgram: GeneratorK[List, Verb, Program] = listGenerator[Verb, Program]
    implicit val itemListToProgram: GeneratorK[List, Item, Program] = listGenerator[Item, Program]

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
