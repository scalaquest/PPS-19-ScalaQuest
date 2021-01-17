package io.github.scalaquest.core.pipeline.interpreter

import io.github.scalaquest.core.model.Model

trait ItemRef

trait ItemRetriever[I] {
  def unapply(ref: ItemRef): Option[I]
}

object ItemRetriever {

  type Builder[M <: Model, I] = Map[ItemRef, I] => ItemRetriever[I]

  def builder[M <: Model](implicit model: M): Builder[model.type, model.I] = apply(model)(_)

  def apply[M <: Model](model: M)(itemsDict: Map[ItemRef, model.I]): ItemRetriever[model.I] = {
    case class SimpleItemRetriever(itemsDict: Map[ItemRef, model.I])
      extends ItemRetriever[model.I] {
      override def unapply(ref: ItemRef): Option[model.I] = itemsDict get ref
    }
    SimpleItemRetriever(itemsDict)
  }
}
