package io.github.scalaquest.core.model

sealed trait Direction

/**
 * All the possible [[Direction]].
 */
object Direction {
  case object North extends Direction
  case object South extends Direction
  case object East  extends Direction
  case object West  extends Direction
  case object Up    extends Direction
  case object Down  extends Direction
}
