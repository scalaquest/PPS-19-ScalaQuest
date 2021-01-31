package io.github.scalaquest.core.dictionary.generators

import cats.implicits.catsKernelStdSemilatticeForSet
import io.github.scalaquest.core.dictionary.{Ditransitive, Intransitive, Program, Transitive, VerbC}
import io.github.scalaquest.core.model.Action.Common.{Inspect, Open, Take}
import io.github.scalaquest.core.model.ItemRef
import io.github.scalaquest.core.model.behaviorBased.impl.SimpleModel
import io.github.scalaquest.core.model.ItemDescription.dsl._

object Test extends App {

  val myVerbs: Set[VerbC] = Set(
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

  import implicits.{verbListGenerator, itemListGenerator}

  val program = combineAll(
    GeneratorK[List, VerbC, Program].generate(myVerbs.toList),
    GeneratorK[List, Item, Program].generate(myItems.toList),
    GeneratorK[List, Item, Program].generate(myItems.toList)
  )
  program
    .map(_.generate)
    .foreach(println)
}
