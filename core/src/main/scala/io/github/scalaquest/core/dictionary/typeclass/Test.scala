package io.github.scalaquest.core.dictionary.typeclass

import io.github.scalaquest.core.dictionary.{Ditransitive, Intransitive, Program, Transitive, Verb}
import io.github.scalaquest.core.model.Action.Common.{Inspect, Open, Take}
import io.github.scalaquest.core.model.{ItemRef, Model}
import io.github.scalaquest.core.model.behaviorBased.impl.SimpleModel
import io.github.scalaquest.core.parsing.scalog.Clause
import io.github.scalaquest.core.model.ItemDescription.dsl._

object aaa extends App {

  type Item = Model#Item

  implicit val monoidProgram: Monoid[Program] = new Monoid[Program] {
    override def unit: Program                            = Set()
    override def combine(a: Program, b: Program): Program = a ++ b
  }

  implicit val itemGenerator: Generator[Item, Program] = new Generator[Item, Program] {

    import io.github.scalaquest.core.parsing.scalog.dsl._

    val name      = CompoundBuilder("name").constructor
    val adjective = CompoundBuilder("adjective").constructor

    override def generate(item: Item): Program = {
      import io.github.scalaquest.core.parsing.scalog.dsl.termToFact
      def names: List[Clause] = List(item.description.base.name).map(name(_))
      def adjectives: List[Clause] =
        item.description.decorators.toList.map(_.name).map(adjective(_))

      (names ++ adjectives).toSet
    }
  }

  implicit val verbGenerator: Generator[Verb, Program] = new Generator[Verb, Program] {
    override def generate(a: Verb): Program = Set(a.clause)
  }

  val myVerbs: Set[Verb] = Set(
    Transitive("take", Take),
    Transitive("pick", Take, Some("up")),
    Ditransitive("open", Open, Some("with")),
    Transitive("open", Open),
    Intransitive("inspect", Inspect)
  )

  val myItems: Set[Item] = Set(
    SimpleModel.SimpleGenericItem(
      i(d("red"), "apple"),
      ItemRef()
    ),
    SimpleModel.SimpleGenericItem(
      i(d("green"), "apple"),
      ItemRef()
    ),
    SimpleModel.SimpleGenericItem(
      i(d("big", "big"), "chest"),
      ItemRef()
    ),
    SimpleModel.SimpleGenericItem(
      i(d("little", "golden"), "key"),
      ItemRef()
    )
  )

  val verbListGenerator = new ListProgramGenerator[Verb]
  val itemListGenerator = new ListProgramGenerator[Item]

  val program = List(
    verbListGenerator.generate(myVerbs.toList),
    itemListGenerator.generate(myItems.toList)
  ).fold(Monoid[Program].unit)(Monoid[Program].combine)
  program
    .map(_.generate)
    .foreach(println)
}

class ListGenerator[A: GeneratorLambda[A, B]#L, B: Monoid] extends Generator[List[A], B] {

  override def generate(as: List[A]): B =
    as.map(Generator[A, B].generate).fold(Monoid[B].unit)(Monoid[B].combine)
}

class ListProgramGenerator[A: ProgramGenerator[A]#L](implicit M: Monoid[Program])
  extends ListGenerator[A, Program]

// Type lambda
class ListProgramGeneratorOld[A: ProgramGenerator[A]#L](implicit M: Monoid[Program])
  extends Generator[List[A], Program] {
  private val generator: Generator[A, Program] = implicitly[Generator[A, Program]]

  override def generate(as: List[A]): Program =
    as.map(generator.generate).fold(Monoid[Program].unit)(Monoid[Program].combine)
}
