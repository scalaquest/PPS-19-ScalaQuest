package io.github.scalaquest.core.model

trait Player[I <: Model#Item] {
  def bag: Set[I]
  def location: Room
}
