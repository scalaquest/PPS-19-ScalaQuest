package io.github.scalaquest.core.model

import java.util.UUID

trait RoomRef

object RoomRef {

  private case class SimpleRoomRef(id: UUID) extends RoomRef

  def apply(): RoomRef = SimpleRoomRef(UUID.randomUUID())
}
