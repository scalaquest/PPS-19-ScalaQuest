package io.github.scalaquest.examples.escaperoom

import io.github.scalaquest.core.model.behaviorBased.impl.SimpleModel.Room
import io.github.scalaquest.core.model.{Direction, RoomRef}

object House {

  def kitchen: myModel.RM =
    Room(
      "kitchen",
      Map(
        Direction.East -> livingRoom.ref
      ),
      Set()
    )

  def livingRoom: myModel.RM =
    Room(
      "living room",
      Map(
      ),
      Set()
    )

  def bathroom: myModel.RM =
    Room(
      "bathroom",
      Map(
      ),
      Set()
    )

  def allTheRooms: Set[myModel.RM] =
    Set(
      kitchen,
      livingRoom,
      bathroom
    )

  def checkRooms: Boolean = allTheRooms.groupBy(_.ref).size == allTheRooms.size

  def genMap: Map[RoomRef, myModel.RM] = allTheRooms.map(r => r.ref -> r).toMap

}
