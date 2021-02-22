package io.github.scalaquest.core.model.behaviorBased.commons.actioning

import io.github.scalaquest.core.model.{Action, Direction}

/**
 * A set of ready-to-use actions, used into various parts of the Common package.
 */
object CommonActions {

  /**
   * Used to take a takeable Item.
   */
  case object Take extends Action

  /**
   * Used to open an openable Item.
   */
  case object Open extends Action

  /**
   * Used to close an openable Item.
   */
  case object Close extends Action

  /**
   * Used to enter in a Room.
   */
  case object Enter extends Action

  /**
   * Used to eat an eatable Item.
   */
  case object Eat extends Action

  /**
   * Used to Inspect a Room.
   */
  case object Inspect extends Action

  /**
   * Used to inspect the player's bag.
   */
  case object InspectBag extends Action

  /**
   * Used to move the player in the selected direction.
   * @param direction
   *   the direction where the player is moved.
   */
  case class Go(direction: Direction) extends Action
}
