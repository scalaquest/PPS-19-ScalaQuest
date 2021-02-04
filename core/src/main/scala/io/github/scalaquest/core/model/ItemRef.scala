package io.github.scalaquest.core.model

/**
 * A unique identifier for a [[Model.Item]].
 */
trait ItemRef

/**
 * Companion object for [[ItemRef]]. Exposes a factory to build the [[ItemRef]] based on an
 * [[ItemDescription]].
 */
object ItemRef {
  private case class StringItemRef(id: String) extends ItemRef
  def apply(itemDescription: ItemDescription): ItemRef = StringItemRef(itemDescription.mkString)
}
