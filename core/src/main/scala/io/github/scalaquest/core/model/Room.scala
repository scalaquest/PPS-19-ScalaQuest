package io.github.scalaquest.core.model

import io.github.scalaquest.core.model.Room.Direction

import java.util.UUID

trait RoomRef

object RoomRef {

  private case class SimpleRoomRef() extends RoomRef {
    private val id: UUID = UUID.randomUUID()
  }

  def apply(): RoomRef = SimpleRoomRef()
}

/**
 * A geographical portion of the match map.
 *
 * This is one of the basic block for the story build by the storyteller, as it is used to identify
 * [[Player]] 's and [[Model.Item]] s' location, in a given moment of the story.
 */
trait Room {

  /**
   * A textual identifier for the room.
   * @return
   *   A textual identifier for the room.
   */
  def name: String

  def id: RoomRef

  override def hashCode(): Int = this.id.hashCode()

  /**
   * Identifies rooms near to the current one, at the cardinal points.
   */
  def neighbors(direction: Direction): Option[Room]

  def items: Set[ItemRef]
}

object Room {

  private case class SimpleRoom(
    name: String,
    _items: () => Set[ItemRef],
    _neighbors: () => Map[Direction, Room]
  ) extends Room {
    override def neighbors(direction: Direction): Option[Room] = _neighbors() get direction

    override def id: RoomRef = RoomRef()

    override def items: Set[ItemRef] = _items()
  }

  def apply(name: String, neighbors: => Map[Direction, Room], items: => Set[ItemRef]): Room =
    SimpleRoom(name, () => items, () => neighbors)

  sealed trait Direction

  object Direction {
    case object North extends Direction
    case object South extends Direction
    case object East  extends Direction
    case object West  extends Direction
    case object Up    extends Direction
    case object Down  extends Direction
  }
}
