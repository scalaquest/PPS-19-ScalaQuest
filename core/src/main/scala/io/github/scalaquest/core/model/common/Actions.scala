package io.github.scalaquest.core.model.common

import io.github.scalaquest.core.model.Action

object Actions {

  case object Take extends Action {
    override val name: String = "take"
  }

  case object Open extends Action {
    override val name: String = "open"
  }

  case object Close extends Action {
    override val name: String = "close"
  }

  case object Enter extends Action {
    override val name: String = "enter"
  }

  case object Eat extends Action {
    override val name: String = "eat"
  }
}
