package io.github.scalaquest.core.model

sealed trait Direction

/**
 * Represents the cardinal points from which it is possible to move, starting from a [[Model.Room]].
 */
object Direction {

  case object North extends Direction {
    override def toString: String = "North"
  }

  case object South extends Direction {
    override def toString: String = "South"
  }

  case object East extends Direction {
    override def toString: String = "East"
  }

  case object West extends Direction {
    override def toString: String = "West"
  }

  case object Up extends Direction {
    override def toString: String = "Up"
  }

  case object Down extends Direction {
    override def toString: String = "Down"
  }

  def all: Set[Direction] = Set(North, South, East, West, Up, Down)
}
