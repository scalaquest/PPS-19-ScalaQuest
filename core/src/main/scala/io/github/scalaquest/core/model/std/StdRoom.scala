package io.github.scalaquest.core.model.std

import io.github.scalaquest.core.model.Direction.Direction
import io.github.scalaquest.core.model.Room

case class StdRoom(name: String, _neighbors: () => Map[Direction, Room]) extends Room {
  override def describe: String                              = s"inspect: $name"
  override def neighbors(direction: Direction): Option[Room] = _neighbors() get direction
}
