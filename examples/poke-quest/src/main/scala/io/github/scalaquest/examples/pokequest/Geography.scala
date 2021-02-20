package io.github.scalaquest.examples.pokequest

import io.github.scalaquest.core.application.ApplicationGeography
import io.github.scalaquest.core.model.Direction

object Geography extends ApplicationGeography[RM] {
  import model.Room

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
