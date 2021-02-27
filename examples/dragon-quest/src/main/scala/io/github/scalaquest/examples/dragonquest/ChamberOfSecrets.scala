package io.github.scalaquest.examples.dragonquest

import io.github.scalaquest.core.application.ApplicationGeography

object ChamberOfSecrets extends ApplicationGeography[RM]{
  import model.Room

  override def allTheRooms: Set[RM] =
    Set(
      chamberOfSecrets
    )

  def chamberOfSecrets: RM =
    Room(
      name = "chamberOfSecrets",
      items = Set(
        Items.basilisk.ref,
        Items.tom.ref,
        Items.tomDiary.ref,
        Items.gryffindorSword.ref)
    )
}
