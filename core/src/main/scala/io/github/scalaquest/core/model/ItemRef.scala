package io.github.scalaquest.core.model

import java.util.UUID

trait ItemRef

object ItemRef {

  private case class SimpleItemRef() extends ItemRef {
    private val id: UUID = UUID.randomUUID()
  }

  def apply(): ItemRef = SimpleItemRef()
}
