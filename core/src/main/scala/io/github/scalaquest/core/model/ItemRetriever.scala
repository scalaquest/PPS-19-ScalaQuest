package io.github.scalaquest.core.model

import io.github.scalaquest.core.model.impl.SimpleModel

trait ItemRef

trait ItemRetriever[I] {
  def unapply(ref: ItemRef): Option[I]
}

object ItemRetriever {

  trait ItemRetrieverBuilder[I] {
    def fromMap(items: Map[ItemRef, I]): ItemRetriever[I]
  }

  /*
   * This can be used like this:
   *
   * implicit val model = SimpleModel
   * val items: Map[ItemRef, model.I] = ???
   * val itemRetriever = ItemRetriever[model.type].fromMap(items)
   */
  def apply[M <: Model](implicit model: M): ItemRetrieverBuilder[model.I] =
    (items: Map[ItemRef, model.I]) => (ref: ItemRef) => items get ref
}
