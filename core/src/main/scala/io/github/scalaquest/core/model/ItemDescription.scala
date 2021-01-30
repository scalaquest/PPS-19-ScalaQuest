package io.github.scalaquest.core.model

import scala.annotation.tailrec

sealed trait ItemDescription

final case class BaseItem(name: String)                                   extends ItemDescription
final case class DecoratedItem(decoration: String, item: ItemDescription) extends ItemDescription

object ItemDescription {

  object dsl {

    case class DecoratorBuilder(decorators: List[String])

    def d(decorator0: String, decorators: String*): DecoratorBuilder =
      DecoratorBuilder((decorator0 +: decorators).toList)

    def i(decoratorBuilder: DecoratorBuilder, base: String): ItemDescription = {
      def go(decorators: List[String]): ItemDescription =
        decorators match {
          case head :: next => DecoratedItem(head, go(next))
          case Nil          => BaseItem(base)
        }
      go(decoratorBuilder.decorators)
    }

    def i(base: String): ItemDescription = BaseItem(base)
  }

  def apply(base: String, decorators: String*): ItemDescription = {
    def go(decorators: List[String]): ItemDescription =
      decorators match {
        case head :: next => DecoratedItem(head, go(next))
        case Nil          => BaseItem(base)
      }
    go(decorators.toList)
  }

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

  def mkString(itemDescription: ItemDescription, separator: String = " "): String = {
    def go: ItemDescription => String = {
      case BaseItem(name)                 => name
      case DecoratedItem(decorator, item) => s"${decorator}${separator}${go(item)}"
    }
    go(itemDescription)
  }

  implicit class EnhancedItemDescription(self: ItemDescription) {
    def isSubset(other: ItemDescription): Boolean = ItemDescription.isSubset(self, other)

    def base: BaseItem = ItemDescription.base(self)

    def decorators: Set[String] = ItemDescription.decorators(self)

    def mkString(separator: String = " "): String = ItemDescription.mkString(self, separator)

    def mkString: String = this.mkString()
  }
}
