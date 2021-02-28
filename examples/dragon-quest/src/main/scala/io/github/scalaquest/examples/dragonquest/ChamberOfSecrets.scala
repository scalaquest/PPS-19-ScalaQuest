package io.github.scalaquest.examples.dragonquest

import io.github.scalaquest.core.application.ApplicationGeography
import io.github.scalaquest.core.model.Direction

object ChamberOfSecrets extends ApplicationGeography[RM] {
  import model.Room

  override def allTheRooms: Set[RM] =
    Set(
      chamberOfSecrets,
      tunnel
    )

  def tunnel: RM =
    Room(
      name = "tunnel",
      neighbors = Map(Direction.West -> chamberOfSecrets.ref),
      items = Set(
        Items.stone.ref
      )
    )

  def chamberOfSecrets: RM =
    Room(
      name = "chamberOfSecrets",
      neighbors = Map(Direction.East -> tunnel.ref),
      items = Set(
        Items.basilisk.ref,
        Items.tom.ref,
        Items.ginny.ref,
        Items.tomDiary.ref,
        Items.sortingHat.ref,
        Items.gryffindorSword.ref
      )
    )
}
