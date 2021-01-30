package io.github.scalaquest.core.dictionary

import io.github.scalaquest.core.model.Action
import io.github.scalaquest.core.model.Action.Common.{Inspect, Open, Take}
import io.github.scalaquest.core.parsing.scalog.Clause

object Test extends App {

  val myVerbs: Set[Verb] = Set(
    Transitive("take", Take),
    Transitive("pick", Take, Some("up")),
    Ditransitive("open", Open, Some("with")),
    Transitive("open", Open),
    Intransitive("inspect", Inspect)
  )

  def toClauses(verbs: List[VerbC]): Program = {
    import generators.implicits.verbListGenerator
    import generators.GeneratorK
    GeneratorK[List, VerbC, Program].generate(verbs)
  }

  def toActions(verbs: List[BaseVerb with Meaning]): Map[VerbPrep, Action] =
    verbs.map(_.binding).toMap

  println(toClauses(myVerbs.toList).map(_.generate).mkString("\n"))
  println(toActions(myVerbs.toList).mkString("\n"))
}
