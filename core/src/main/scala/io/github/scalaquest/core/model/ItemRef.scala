package io.github.scalaquest.core.model

import java.util.UUID

trait ItemRef

object ItemRef {

  private case class SimpleItemRef(id: UUID) extends ItemRef
  def apply(): ItemRef = SimpleItemRef(UUID.randomUUID())

  private case class StringItemRef(id: String) extends ItemRef
  def apply(itemDescription: ItemDescription): ItemRef = StringItemRef(itemDescription.mkString)
}
