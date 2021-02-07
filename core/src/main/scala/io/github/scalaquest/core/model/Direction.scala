package io.github.scalaquest.core.model

sealed trait Direction

/**
 * Represents the cardinal points from which it is possible to move, starting from a [[Model.Room]].
 */
object Direction {
  case object North extends Direction
  case object South extends Direction
  case object East  extends Direction
  case object West  extends Direction
  case object Up    extends Direction
  case object Down  extends Direction

  def all: Set[Direction] = Set(North, South, East, West, Up, Down)
}
