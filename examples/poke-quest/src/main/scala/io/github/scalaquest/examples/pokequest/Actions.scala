package io.github.scalaquest.examples.pokequest

import io.github.scalaquest.core.model.Action
import io.github.scalaquest.core.dictionary.verbs.{Ditransitive, Transitive, Verb}

/**
 * Custom actions required by the example, and the verbs linked to them.
 */
object Actions {
  case object Play   extends Action
  case object Attack extends Action
  case object Wake   extends Action
  case object Move   extends Action
  case object Catch  extends Action
  case object Throw  extends Action

  def verbs: Set[Verb] =
    Set(
      Transitive("play", Play),
      Transitive("wake", Wake),
      Ditransitive("wake", Wake, Some("with")),
      Transitive("hit", Attack),
      Ditransitive("hit", Attack, Some("with")),
      Transitive("attack", Attack),
      Ditransitive("attack", Attack, Some("with")),
      Transitive("move", Move),
      Ditransitive("move", Move, Some("with")),
      Transitive("catch", Catch),
      Ditransitive("catch", Catch, Some("with")),
      Transitive("throw", Throw),
      Ditransitive("throw", Throw, Some("to"))
    )
}
