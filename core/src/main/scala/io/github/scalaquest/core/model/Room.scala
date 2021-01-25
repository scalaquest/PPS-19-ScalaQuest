package io.github.scalaquest.core.model

import io.github.scalaquest.core.model.Room.Direction

/**
 * A geographical portion of the match map.
 *
 * This is one of the basic block for the story build by the storyteller, as it is used to identify
 * [[Player]] 's and [[Model.Item]] s' location, in a given moment of the story.
 */
trait Room {

  /**
   * A textual identifier for the room.
   * @return
   *   A textual identifier for the room.
   */
  def name: String

  /**
   * Identifies rooms near to the current one, at the cardinal points.
   */
  def neighbors(direction: Direction): Option[Room]
}

case class SimpleRoom private (name: String, _neighbors: () => Map[Direction, Room]) extends Room {
  override def neighbors(direction: Direction): Option[Room] = _neighbors() get direction
}

object Room {

  def apply(name: String, neighbors: => Map[Direction, Room]): Room =
    SimpleRoom(name, () => neighbors)

  sealed trait Direction

  object Direction {
    case object North extends Direction
    case object South extends Direction
    case object East  extends Direction
    case object West  extends Direction
    case object Up    extends Direction
    case object Down  extends Direction
  }
}
