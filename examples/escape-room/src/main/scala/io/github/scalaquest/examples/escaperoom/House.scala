package io.github.scalaquest.examples.escaperoom

import io.github.scalaquest.core.model.behaviorBased.impl.SimpleModel.Room
import io.github.scalaquest.core.model.{Direction, RoomRef}

object House {

  def kitchen: Room =
    Room(
      "kitchen",
      Map(
        Direction.East -> livingRoom.ref
      ),
      Set()
    )

  def livingRoom: Room =
    Room(
      "living room",
      Map(
      ),
      Set()
    )

  def bathroom: Room =
    Room(
      "bathroom",
      Map(
      ),
      Set()
    )

  def allTheRooms: Set[Room] =
    Set(
      kitchen,
      livingRoom,
      bathroom
    )

  def checkRooms: Boolean = allTheRooms.groupBy(_.ref).size == allTheRooms.size

  def genMap: Map[RoomRef, Room] = allTheRooms.map(r => r.ref -> r).toMap

}
