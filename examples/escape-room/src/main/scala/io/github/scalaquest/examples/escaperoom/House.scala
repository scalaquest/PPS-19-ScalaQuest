package io.github.scalaquest.examples.escaperoom

import io.github.scalaquest.core.application.Environment
import io.github.scalaquest.core.model.Direction

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
      Map(),
      Set(
        Items.redApple.ref,
        Items.livingRoomKey.ref,
        Items.livingRoomDoor.ref
      )
    )

  def livingRoom: Room =
    RoomFactory(
      "living room",
      Map(
        Direction.North -> bathroom.ref,
        Direction.West  -> kitchen.ref
      ),
      Set(
        Items.kitchenDoor.ref
      )
    )

  def bathroom: Room =
    RoomFactory(
      "bathroom",
      Map(
        Direction.South -> livingRoom.ref
      ),
      Set()
    )

}
