/*
 * Copyright (c) 2021 ScalaQuest Team.
 *
 * This file is part of ScalaQuest, and is distributed under the terms of the MIT License as
 * described in the file LICENSE in the ScalaQuest distribution's top directory.
 */

package io.github.scalaquest.examples.pokequest

import io.github.scalaquest.core.application.ApplicationGeography
import io.github.scalaquest.core.model.Direction
import model.Room

/**
 * The rooms required by the example.
 */
object Geography extends ApplicationGeography[RM] {

  override def allTheRooms: Set[RM] =
    Set(
      vermillionCity,
      forest
    )

  def vermillionCity: RM =
    Room(
      name = "Vermilion City",
      items = Set(Items.snorlax.ref)
    )

  def forest: RM =
    Room(
      name = "forest",
      neighbors = Map(Direction.South -> vermillionCity.ref),
      items = Set(
        Items.charizard.ref
      )
    )
}
