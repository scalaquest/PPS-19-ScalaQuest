package io.github.scalaquest.core.model

import io.github.scalaquest.core.model.Direction.Direction
import io.github.scalaquest.core.model.std.StdRoom

object Direction extends Enumeration {
  type Direction = Value
  val NORTH, SOUTH, WEST, EAST, UP, DOWN = Value
}

trait Room {
  def name: String
  // def items: Set[I]
  def neighbors(direction: Direction): Option[Room]
}

object Room {
  def apply(name: String, neighbors: () => Map[Direction, Room]): Room = StdRoom(name, neighbors)
}
