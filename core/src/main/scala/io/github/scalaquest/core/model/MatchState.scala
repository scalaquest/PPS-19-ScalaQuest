package io.github.scalaquest.core.model

/**
 * Represents the current state of the match.
 * @tparam I
 *   the specific implementation of the [[Model.Item]].
 */
trait MatchState[I <: Model#Item, RM <: Model#Room] {

  /**
   * All the [[Model.Item]] that a [[Player]] could see.
   * @return
   */
  def itemsInScope: Set[I] = (player.bag ++ rooms(player.location).items).flatMap(items.get(_))

  /**
   * The [[Player]] involved into the match. As it is a core concept, an instance of [[Player]] is
   * included into the [[MatchState]].
   * @return
   *   The current [[Player]].
   */
  def player: Player

  /**
   * Indicates whether the match has reached the end. When true, the entire match ende after the
   * current pipeline round.
   * @return
   *   True if the match has to end after the current round, false otherwise.
   */
  def ended: Boolean

  /**
   * Represents the configuration of the match, in terms of [[Model.Room]] s and [[Model.Item]].
   * @return
   *   a [[Set]] representing all [[Model.Room]] s of the match, and the [[Model.Item]] s in them.
   */
  def rooms: Map[RoomRef, RM]

  /**
   * A [[Map]] with all the [[Model.Item]] s present in the match. For each [[ItemRef]] is found the
   * specific [[Model.Item]] in the match.
   * @return
   */
  def items: Map[ItemRef, I]
}
