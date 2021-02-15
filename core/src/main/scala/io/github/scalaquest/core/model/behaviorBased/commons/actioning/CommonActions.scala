package io.github.scalaquest.core.model.behaviorBased.commons.actioning

import io.github.scalaquest.core.model.{Action, Direction}

/**
 * [[Action]] s already implemented in the game.
 */
object CommonActions {
  case object Take                    extends Action
  case object Open                    extends Action
  case object Close                   extends Action
  case object Enter                   extends Action
  case object Eat                     extends Action
  case object Inspect                 extends Action
  case object InspectBag              extends Action
  case class Go(direction: Direction) extends Action
}
