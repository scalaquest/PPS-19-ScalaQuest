package io.github.scalaquest.core.application

import io.github.scalaquest.core.model.RoomRef
import io.github.scalaquest.core.model.Model

abstract class Environment[R <: Model#Room] {
  def allTheRooms: Set[R]

  def checkRooms: Boolean = allTheRooms.groupBy(_.ref).size == allTheRooms.size

  def refToRoom: Map[RoomRef, R] = allTheRooms.map(r => r.ref -> r).toMap
}
