/*
 * Copyright (c) 2021 ScalaQuest Team.
 *
 * This file is part of ScalaQuest, and is distributed under the terms of the MIT License as
 * described in the file LICENSE in the ScalaQuest distribution's top directory.
 */

package io.github.scalaquest.examples.wizardquest

import io.github.scalaquest.core.dictionary.verbs.{Ditransitive, Transitive, Verb}
import io.github.scalaquest.core.model.Action

/**
 * Custom actions required by the example, and the verbs linked to them.
 */
object Actions {
  case object Attack     extends Action
  case object Throw      extends Action
  case object LookInside extends Action

  def verbs: Set[Verb] =
    Set(
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
