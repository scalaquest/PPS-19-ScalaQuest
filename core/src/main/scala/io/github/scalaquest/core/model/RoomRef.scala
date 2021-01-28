package io.github.scalaquest.core.model

/**
 * A unique identifier for a [[Model.Room]].
 */
trait RoomRef

/**
 * Companion object for [[RoomRef]]. Exposes some factories to build it.
 */
object RoomRef {

  private case class StringRef(name: String) extends RoomRef
  def apply(roomName: String): RoomRef = StringRef(roomName)
}
