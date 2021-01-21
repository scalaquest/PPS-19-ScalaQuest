package io.github.scalaquest.core.pipeline.interpreter

import io.github.scalaquest.core.model.{ItemRef, Model}

trait RefToItem[I] {
  def unapply(ref: ItemRef): Option[I]
}

object RefToItem {

  type Builder[M <: Model, I] = Map[ItemRef, I] => RefToItem[I]

  def builder[M <: Model](implicit model: M): Builder[model.type, model.I] = apply(model)(_)

  def apply[M <: Model](model: M)(itemsDict: Map[ItemRef, model.I]): RefToItem[model.I] = {
    case class SimpleRefToItem(itemsDict: Map[ItemRef, model.I]) extends RefToItem[model.I] {
      override def unapply(ref: ItemRef): Option[model.I] = itemsDict get ref
    }
    SimpleRefToItem(itemsDict)
  }
}
