package io.github.scalaquest.examples.pokequest

import io.github.scalaquest.core.model.Action
import io.github.scalaquest.core.dictionary.verbs.{Ditransitive, Transitive, Verb}

object Actions {
  case object Play extends Action
  case object Hit  extends Action
  case object Wake extends Action
  case object Move extends Action

  def customVerbs: Set[Verb] =
    Set(
      Transitive("play", Play),
      Transitive("wake", Wake),
      Ditransitive("wake", Wake, Some("with")),
      Transitive("hit", Hit),
      Ditransitive("hit", Hit, Some("with")),
      Transitive("move", Move),
      Ditransitive("move", Move, Some("with"))
    )
}
