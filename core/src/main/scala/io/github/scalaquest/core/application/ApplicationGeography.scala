/*
 * Copyright (c) 2021 ScalaQuest Team.
 *
 * This file is part of ScalaQuest, and is distributed under the terms of the MIT License as
 * described in the file LICENSE in the ScalaQuest distribution's top directory.
 */

package io.github.scalaquest.core.application

import io.github.scalaquest.core.model.{Model, RoomRef}

abstract class ApplicationGeography[RM <: Model#Room] {
  def allTheRooms: Set[RM]

  def checkRooms: Boolean = allTheRooms.groupBy(_.ref).size == allTheRooms.size

  def refToRoom: Map[RoomRef, RM] = allTheRooms.map(r => r.ref -> r).toMap
}
