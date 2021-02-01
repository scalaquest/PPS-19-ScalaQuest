package io.github.scalaquest.core.dictionary

import cats.implicits.{catsKernelStdMonoidForMap, catsStdInstancesForList}
import cats.kernel.Semigroup
import io.github.scalaquest.core.dictionary.generators.{Generator, GeneratorK, Item, combineAll}
import io.github.scalaquest.core.model.{Action, ItemDescription, ItemRef}
import io.github.scalaquest.core.model.Action.Common.{Inspect, Open, Take}
import io.github.scalaquest.core.model.ItemDescription.dsl.{d, i}
import io.github.scalaquest.core.model.behaviorBased.impl.SimpleModel

object Test extends App {

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

  def verbsToClauses(verbs: List[VerbC]): Program = {
    import generators.implicits.verbListGenerator
    import generators.GeneratorK
    GeneratorK[List, VerbC, Program].generate(verbs)
  }

  def verbsToActions(verbs: List[Verb]): Map[VerbPrep, Action] = {
    implicit val actionSemigroup: Semigroup[Action] = Semigroup.instance((_, b) => b)

    implicit val verbToEntry: Generator[Verb, Map[VerbPrep, Action]] =
      Generator.instance(v => Map(v.binding))

    implicit val listToMapAction =
      new GeneratorK[List, Verb, Map[VerbPrep, Action]]

    GeneratorK[List, Verb, Map[VerbPrep, Action]].generate(verbs)
  }

  def itemsToClauses(items: List[Item]): Program = {
    import generators.implicits.itemListGenerator
    GeneratorK[List, Item, Program].generate(items)
  }

  def itemsToMap(items: List[Item]): Map[ItemDescription, ItemRef] = {
    implicit val itemRefSemigroup: Semigroup[ItemRef] = Semigroup.instance((_, b) => b)
    implicit val itemToEntry: Generator[Item, Map[ItemDescription, ItemRef]] =
      Generator.instance(a => Map(a.description -> a.ref))
    implicit val listToMapItems =
      new GeneratorK[List, Item, Map[ItemDescription, ItemRef]]()
    GeneratorK[List, Item, Map[ItemDescription, ItemRef]].generate(items)
  }

  println(itemsToClauses(myItems.toList).map(_.generate).mkString("\n"))
  println(verbsToClauses(myVerbs.toList).map(_.generate).mkString("\n"))
  println("---")
  println(itemsToMap(myItems.toList).mkString("\n"))
  println(verbsToActions(myVerbs.toList).mkString("\n"))
}
