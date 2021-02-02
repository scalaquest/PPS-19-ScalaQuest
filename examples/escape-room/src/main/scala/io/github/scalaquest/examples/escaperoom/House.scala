package io.github.scalaquest.examples.escaperoom

import io.github.scalaquest.core.application.Environment
import io.github.scalaquest.core.model.{Direction}

object House extends Environment[Room] {
  import model.{Room => RoomFactory}

  override def allTheRooms: Set[Room] =
    Set(
      kitchen,
      livingRoom,
      bathroom
    )

  def kitchen: Room =
    RoomFactory(
      "kitchen",
      Map(
        Direction.East -> livingRoom.ref
      ),
      Set(
        refToItem.head._1
      )
    )

  def livingRoom: Room =
    RoomFactory(
      "living room",
      Map(),
      Set()
    )

  def bathroom: Room =
    RoomFactory(
      "bathroom",
      Map(),
      Set()
    )

}
