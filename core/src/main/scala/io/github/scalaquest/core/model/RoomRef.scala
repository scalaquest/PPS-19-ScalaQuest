package io.github.scalaquest.core.model

import java.util.UUID

/**
 * A unique identifier for a [[Model.Room]].
 */
trait RoomRef

/**
 * Companion object for [[RoomRef]]. Exposes some factories to build it.
 */
object RoomRef {

  private case class SimpleRoomRef(id: UUID) extends RoomRef
  def apply(): RoomRef = SimpleRoomRef(UUID.randomUUID())

  private case class StringRef(name: String) extends RoomRef
  def apply(roomName: String): RoomRef = StringRef(roomName)
}
