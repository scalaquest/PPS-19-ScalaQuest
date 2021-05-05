/*
 * Copyright (c) 2021 ScalaQuest Team.
 *
 * This file is part of ScalaQuest, and is distributed under the terms of the MIT License as
 * described in the file LICENSE in the ScalaQuest distribution's top directory.
 */

package io.github.scalaquest.examples.wizardquest

import io.github.scalaquest.core.application.ApplicationGeography
import io.github.scalaquest.core.model.Direction
import model.Room

/**
 * The rooms required by the example.
 */
object Geography extends ApplicationGeography[RM] {

  def tunnel: RM = Room(name = "tunnel", items = Set(Items.stone.ref))

  def chamberOfSecrets: RM =
    Room(
      name = "chamber of Secrets",
      neighbors = Map(Direction.East -> tunnel.ref),
      items = Set(
        Items.basilisk.ref,
        Items.tom.ref,
        Items.tomDiary.ref,
        Items.ginny.ref,
        Items.sortingHat.ref
      )
    )

  override def allTheRooms: Set[RM] = Set(chamberOfSecrets, tunnel)
}
