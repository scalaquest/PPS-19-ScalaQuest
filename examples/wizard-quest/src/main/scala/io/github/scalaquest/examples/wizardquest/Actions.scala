package io.github.scalaquest.examples.wizardquest

import io.github.scalaquest.core.dictionary.verbs.{Ditransitive, Transitive, Verb}
import io.github.scalaquest.core.model.Action

object Actions {

  case object Attack     extends Action
  case object Throw      extends Action
  case object LookInside extends Action

  def customVerbs: Set[Verb] =
    Set(
      Transitive("look", LookInside, Some("inside")),
      Transitive("hit", Attack),
      Ditransitive("hit", Attack, Some("with")),
      Transitive("attack", Attack),
      Ditransitive("attack", Attack, Some("with")),
      Transitive("kill", Attack),
      Ditransitive("kill", Attack, Some("with")),
      Transitive("destroy", Attack),
      Ditransitive("destroy", Attack, Some("with")),
      Transitive("throw", Throw)
    )
}
