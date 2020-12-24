package io.github.scalaquest.core.model

import io.github.scalaquest.core.model.Direction.Direction

object Direction extends Enumeration {
  type Direction = Value
  val NORTH, SOUTH, WEST, EAST, UP, DOWN = Value
}

trait Room {
  def name: String
  def describe: String
  def neighbors(direction: Direction): Option[Room]
}

case class SimpleRoom(name: String, _neighbors: () => Map[Direction, Room]) extends Room {
  override def describe: String = s"inspect: ${name}"
  override def neighbors(direction: Direction): Option[Room] = _neighbors() get direction
}
