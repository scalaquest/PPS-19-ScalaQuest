package io.github.scalaquest.examples.escaperoom

import io.github.scalaquest.core.application.ApplicationGeography
import io.github.scalaquest.core.model.Direction

object House extends ApplicationGeography[RM] {
  import model.Room

  override def allTheRooms: Set[RM] =
    Set(
      basement,
      livingRoom,
      bathroom
    )

  def basement: RM =
    Room(
      name = "basement",
      items = Set(Items.chest.ref, Items.crowbar.ref, Items.hatch.ref)
    )

  def livingRoom: RM =
    Room(
      name = "living room",
      neighbors = Map(Direction.North -> bathroom.ref, Direction.Down -> basement.ref),
      items =
        Set(Items.redApple.ref, Items.greenApple.ref, Items.doorway.ref, Items.basementHatch.ref)
    )

  def bathroom: RM =
    Room(
      "bathroom",
      Map(Direction.South -> livingRoom.ref)
    )
}
