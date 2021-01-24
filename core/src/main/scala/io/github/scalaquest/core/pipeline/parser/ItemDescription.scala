package io.github.scalaquest.core.pipeline.parser

import scala.annotation.tailrec

sealed trait ItemDescription
final case class BaseItem(name: String)                                   extends ItemDescription
final case class DecoratedItem(decoration: String, item: ItemDescription) extends ItemDescription

object ItemDescription {

  @tailrec
  def base(d: ItemDescription): BaseItem =
    d match {
      case i: BaseItem         => i
      case DecoratedItem(_, i) => base(i)
    }

  def decorators(d: ItemDescription): Set[String] = {
    @tailrec
    def go(d: ItemDescription, acc: Set[String]): Set[String] =
      d match {
        case DecoratedItem(decoration, item) => go(item, acc + decoration)
        case _                               => acc
      }
    go(d, Set())
  }

  def isSubset(d1: ItemDescription, d2: ItemDescription): Boolean =
    base(d1) == base(d2) && decorators(d1).subsetOf(decorators(d2))

  implicit class EnhancedItemDescription(self: ItemDescription) {
    def isSubset(other: ItemDescription): Boolean = ItemDescription.isSubset(self, other)

    def base: BaseItem = ItemDescription.base(self)

    def decorators: Set[String] = ItemDescription.decorators(self)
  }
}
