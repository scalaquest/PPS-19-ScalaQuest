package io.github.scalaquest.core.application

import io.github.scalaquest.core.model.{Model, RoomRef}

abstract class Environment[RM <: Model#Room] {
  def allTheRooms: Set[RM]

  def checkRooms: Boolean = allTheRooms.groupBy(_.ref).size == allTheRooms.size

  def refToRoom: Map[RoomRef, RM] = allTheRooms.map(r => r.ref -> r).toMap
}
