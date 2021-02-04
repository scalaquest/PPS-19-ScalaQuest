package io.github.scalaquest.core.model

/**
 * Represents the main properties of the character impersonated by the user.
 */
trait Player {

  /**
   * References to the [[Model.Item]] s that the player brings with him. In a concrete story, this
   * is not necessarily a real bag: it is simply an intuitive term to refer to this set of items.
   * @return
   */
  def bag: Set[ItemRef]

  /**
   * Reference for the [[Model.Room]] where the player is currently positioned.
   * @return
   *   The current location of the player, as a [[RoomRef]].
   */
  def location: RoomRef
}
