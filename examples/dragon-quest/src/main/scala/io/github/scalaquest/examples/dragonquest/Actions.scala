package io.github.scalaquest.examples.dragonquest

import io.github.scalaquest.core.dictionary.verbs.{Ditransitive, Transitive, Verb}
import io.github.scalaquest.core.model.Action

object Actions {
  case object Attack extends Action


  def customVerbs: Set[Verb] =
    Set(
      Transitive("hit", Attack),
      Ditransitive("hit", Attack, Some("with")),
      Transitive("attack", Attack),
      Ditransitive("attack", Attack, Some("with")),
      Transitive("kill", Attack),
      Ditransitive("kill", Attack, Some("with"))
    )
}
