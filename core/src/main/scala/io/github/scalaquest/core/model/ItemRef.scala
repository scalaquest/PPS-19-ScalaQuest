package io.github.scalaquest.core.model

trait ItemRef

object ItemRef {

  private case class StringItemRef(id: String) extends ItemRef
  def apply(itemDescription: ItemDescription): ItemRef = StringItemRef(itemDescription.mkString)
}
