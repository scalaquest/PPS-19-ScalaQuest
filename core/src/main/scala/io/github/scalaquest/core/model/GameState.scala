package io.github.scalaquest.core.model

trait GameState[I <: Model#Item] {
  def player: Player[I]
  def ended: Boolean
  def rooms: Set[Room]
  def itemsInRooms: Map[Room, Set[I]]
  // def itemsInRoom(room: Room): Set[I]

  def itemsInScope: Set[I] = player.bag ++ itemsInRooms.getOrElse(player.location, Set())
}
