/*
 * Copyright (c) 2021 ScalaQuest Team.
 *
 * This file is part of ScalaQuest, and is distributed under the terms of the MIT License as
 * described in the file LICENSE in the ScalaQuest distribution's top directory.
 */

package io.github.scalaquest.examples.escaperoom

import io.github.scalaquest.core.application.ApplicationGeography
import io.github.scalaquest.core.model.Direction
import model.Room

/**
 * The rooms required by the example.
 */
object Geography extends ApplicationGeography[RM] {

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
