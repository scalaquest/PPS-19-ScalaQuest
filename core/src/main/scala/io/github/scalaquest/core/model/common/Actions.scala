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

  case object GoNorth extends Action {
    override val name: String = "go n"
  }

  case object GoSouth extends Action {
    override val name: String = "go s"
  }

  case object GoEast extends Action {
    override val name: String = "go e"
  }

  case object GoWest extends Action {
    override val name: String = "go w"
  }

  case object GoUp extends Action {
    override val name: String = "go u"
  }

  case object GoDown extends Action {
    override val name: String = "go d"
  }

  case object Inspect extends Action {
    override val name: String = "inspect"
  }
}
