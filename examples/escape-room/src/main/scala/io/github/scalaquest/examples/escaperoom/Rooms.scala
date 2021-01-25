package io.github.scalaquest.examples.escaperoom

import io.github.scalaquest.core.model.Room.Direction
import io.github.scalaquest.core.model.{Room}

object House {

  def kitchen: Room =
    Room(
      "kitchen",
      Map(
        Direction.East -> livingRoom
      )
    )

  def livingRoom: Room =
    Room(
      "living room",
      Map(
      )
    )

  def bathroom: Room =
    Room(
      "bathroom",
      Map(
      )
    )

}
