package io.github.scalaquest.core.dictionary

import io.github.scalaquest.core.model.Action
import io.github.scalaquest.core.model.Action.Common.{Inspect, Open, Take}
import io.github.scalaquest.core.parsing.scalog.Clause

object Test extends App {

  val myVerbs: Set[Verb] = Set(
    Transitive("take", Take),
    Transitive("pick up", Take),
    Ditransitive("open", "with", Open),
    Transitive("open", Open),
    Intransitive("inspect", Inspect)
  )

  def toClauses(verbs: List[Verb with GenerateClause]): List[Clause] = verbs.map(_.clause)

  def toActions(verbs: List[BaseVerb with GeneratePair]): Map[String, Action] =
    verbs.map(_.pair).toMap

  println(toClauses(myVerbs.toList).map(_.generate).mkString("\n"))
  println(toActions(myVerbs.toList).mkString("\n"))
}
