package io.github.scalaquest.core.model

/**
 * Represents the current state of the match.
 * @tparam I
 *   the specific implementation of the [[Model.Item]].
 */
trait MatchState[I <: Model#Item] {

  /**
   * The player involved into the match. As it is a core concept, an instance of [[Player]] is
   * included into the [[MatchState]].
   * @return
   *   The current [[Player]].
   */
  def player: Player[I]

  /**
   * Indicates whether the match has reached the end. When true, the entire match ende after the
   * current pipeline round.
   * @return
   *   True if the match has to end after the current round, false otherwise.
   */
  def ended: Boolean

  /**
   * Represents the configuration match, in terms of [[Room]] s and [[Model.Item]].
   * @return
   *   a [[Map]] representing all [[Room]] s of the match, and the [[Model.Item]] s in them.
   */
  def geography: Map[Room, Set[I]]
}
