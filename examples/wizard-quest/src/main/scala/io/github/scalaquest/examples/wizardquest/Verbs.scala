package io.github.scalaquest.examples.wizardquest

import io.github.scalaquest.core.dictionary.verbs.{Ditransitive, Transitive, Verb}
import io.github.scalaquest.core.model.behaviorBased.commons.actioning.CVerbs

/**
 * The custom set of verbs required by the example.
 */
object Verbs {

  def allTheVerbs: Set[Verb] =
    CVerbs() ++ Set(
      Transitive("look", Actions.LookInside, Some("inside")),
      Transitive("hit", Actions.Attack),
      Ditransitive("hit", Actions.Attack, Some("with")),
      Transitive("attack", Actions.Attack),
      Ditransitive("attack", Actions.Attack, Some("with")),
      Transitive("kill", Actions.Attack),
      Ditransitive("kill", Actions.Attack, Some("with")),
      Transitive("destroy", Actions.Attack),
      Ditransitive("destroy", Actions.Attack, Some("with")),
      Transitive("throw", Actions.Throw)
    )
}
