package io.github.scalaquest.core.model

/**
 * A unique identifier for a [[Model.Room]].
 */
trait RoomRef

/**
 * Companion object for [[RoomRef]]. Exposes a factory to build the [[RoomRef]] based on a
 * [[String]] (hopefully, the room name).
 */
object RoomRef {

  /**
   * The String Reference to a [[Model.Room]].
   * @param name
   *   the name of [[Model.Room]].
   */
  private final case class StringRef(name: String) extends RoomRef

  /**
   * A factory to build the [[RoomRef]] based on a [[String]] (hopefully, the room name).
   * @param roomName
   *   the (hopefully) room name.
   * @return
   */
  def apply(roomName: String): RoomRef = StringRef(roomName)
}
