package io.github.scalaquest.core.model.common

import io.github.scalaquest.core.model.Action
import io.github.scalaquest.core.model.Direction.Direction

object Actions {

  case object Take                    extends Action
  case object Open                    extends Action
  case object Close                   extends Action
  case object Enter                   extends Action
  case object Eat                     extends Action
  case object Inspect                 extends Action
  case class Go(direction: Direction) extends Action
}
