package io.github.scalaquest.core.model

/**
 * Represents the main properties of the character impersonated by the user.
 * @tparam I
 *   a subtype of [[Model.Item]], the one effectively used into the model implementation.
 */
trait Player[I <: Model#Item] {

  /**
   * [[Model.Item]] s that the player brings with him. In a contrete story, this not necessarily a
   * real bag: it is simply an intuitive term to refer to this set of items.
   * @return
   */
  def bag: Set[I]

  /**
   * The current location of the player into the map.
   * @return
   *   The current location of the player into the map.
   */
  def location: Room
}
