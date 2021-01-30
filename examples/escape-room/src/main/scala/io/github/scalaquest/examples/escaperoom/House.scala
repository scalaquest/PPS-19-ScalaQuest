package io.github.scalaquest.examples.escaperoom

import io.github.scalaquest.core.model.behaviorBased.impl.SimpleModel.{Room => RoomFactory}
import io.github.scalaquest.core.model.{Direction, RoomRef}

object House {

  def kitchen: Room =
    RoomFactory(
      "kitchen",
      Map(
        Direction.East -> livingRoom.ref
      ),
      Set(
        items.head._1
      )
    )

  def livingRoom: Room =
    RoomFactory(
      "living room",
      Map(
      ),
      Set()
    )

  def bathroom: Room =
    RoomFactory(
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
