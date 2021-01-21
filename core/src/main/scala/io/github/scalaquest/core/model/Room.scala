package io.github.scalaquest.core.model

import io.github.scalaquest.core.model.Room.Direction

trait Room {
  def name: String
  def neighbors(direction: Direction): Option[Room]
}

case class StdRoom(name: String, _neighbors: () => Map[Direction, Room]) extends Room {
  override def neighbors(direction: Direction): Option[Room] = _neighbors() get direction
}

object Room {
  def apply(name: String, neighbors: () => Map[Direction, Room]): Room = StdRoom(name, neighbors)

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
