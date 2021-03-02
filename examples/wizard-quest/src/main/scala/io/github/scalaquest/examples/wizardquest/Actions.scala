package io.github.scalaquest.examples.wizardquest

import io.github.scalaquest.core.model.Action

/**
 * Custom actions required by the example.
 */
object Actions {
  case object Attack     extends Action
  case object Throw      extends Action
  case object LookInside extends Action
}
