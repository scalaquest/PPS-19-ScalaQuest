package io.github.scalaquest.core.model

import scala.annotation.tailrec

/**
 * A trait that is used for describe an Item. The description of the object should be univocal.
 */
sealed trait ItemDescription

/**
 * The common name of the Item.
 * @param name
 *   of the Item.
 */
final case class BaseItem(name: String) extends ItemDescription

/**
 * The description of the name.
 * @param decoration
 *   should be an adjective that adds specificity to the BaseItem name.
 * @param item
 *   could be:
 *   - an other DecoratedItem to add some more specificity
 *   - the BaseItem
 */
final case class DecoratedItem(decoration: String, item: ItemDescription) extends ItemDescription

/**
 * Contain some methods to facilitates the build of an ItemDescription.
 */
object ItemDescription {

  /**
   * An object that contain some methods in order to add more declarativeness to the
   * ItemDescription.
   */
  object dsl {

    /**
     * A class with all the decorations for an ItemDescription.
     * @param decorators
     *   all the decorators
     */
    final case class DecoratorBuilder(decorators: List[String])

    /**
     * Create a DecoratorBuilder with all the decorations. The first is mandatory.
     * @param decorator0
     *   the first decorator term.
     * @param decorators
     *   the other decorator terms.
     * @return
     */
    def d(decorator0: String, decorators: String*): DecoratorBuilder =
      DecoratorBuilder((decorator0 +: decorators).toList)

    /**
     * Create an ItemDescription given the name and a DecoratorBuilder with all the decorations.
     * @param decoratorBuilder
     *   the decorationBuilder.
     * @param base
     *   the name of the ItemDescription.
     * @return
     *   the ItemDescription.
     */
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

  /**
   * A method that facilitates the creation for ItemDescription avoiding the creation of all the
   * Structures.
   * @param base
   *   the name for BaseItem.
   * @param decorators
   *   all the adjectives for
   * @return
   *   the ItemDescription.
   */
  def apply(base: String, decorators: String*): ItemDescription = {
    def go(decorators: List[String]): ItemDescription =
      decorators match {
        case head :: next => DecoratedItem(head, go(next))
        case Nil          => BaseItem(base)
      }
    go(decorators.toList)
  }

  /**
   * It individuates the base of an ItemDescription.
   * @param d
   *   the ItemDescription.
   * @return
   *   the BaseItem.
   */
  @tailrec
  def base(d: ItemDescription): BaseItem =
    d match {
      case i: BaseItem         => i
      case DecoratedItem(_, i) => base(i)
    }

  /**
   * It individuates all the decorations of an ItemDescription.
   * @param d
   *   the ItemDescription.
   * @return
   *   the all the adjectives.
   */
  def decorators(d: ItemDescription): Set[String] = {
    @tailrec
    def go(d: ItemDescription, acc: Set[String]): Set[String] =
      d match {
        case DecoratedItem(decoration, item) => go(item, acc + decoration)
        case _                               => acc
      }
    go(d, Set())
  }

  /**
   * It individuates if two Items have a common part. In order to do this, there are two fundamental
   * properties:
   *   - items must have the same base.
   *   - decorators must be subsets of each other.
   * @param d1
   *   first ItemDescription.
   * @param d2
   *   second ItemDescription.
   * @return
   */
  def isSubset(d1: ItemDescription, d2: ItemDescription): Boolean =
    base(d1) == base(d2) && decorators(d1).subsetOf(decorators(d2))

  /**
   * Prepare in a String the description of an ItemDescription. Start with adjectives, end with
   * BaseItem.
   * @param itemDescription
   *   the itemDescription that have to be printed.
   * @param separator
   *   the separation between each word printed.
   * @return
   *   the description.
   */
  def mkString(itemDescription: ItemDescription, separator: String = " "): String = {
    def go: ItemDescription => String = {
      case BaseItem(name)                 => name
      case DecoratedItem(decorator, item) => s"${decorator}${separator}${go(item)}"
    }
    go(itemDescription)
  }

  /**
   * A class that add some more useful methods for ItemDescription.
   * @param self
   *   the ItemDescription wrapped into [[EnhancedItemDescription]].
   */
  implicit final class EnhancedItemDescription(self: ItemDescription) {

    /**
     * Compare this ItemDescription with an other using ItemDescription method 'isSubset'.
     * @param other
     *   the other ItemDescription.
     * @return
     *   True if this is subset of the 'other' ItemDescription. False otherwise
     */
    def isSubset(other: ItemDescription): Boolean = ItemDescription.isSubset(self, other)

    /**
     * The baseItem of this ItemDescription.
     * @return
     *   The baseItem of this ItemDescription.
     */
    def base: BaseItem = ItemDescription.base(self)

    /**
     * The decorations of this ItemDescription.
     * @return
     *   The decorations of this ItemDescription.
     */
    def decorators: Set[String] = ItemDescription.decorators(self)

    /**
     * The description of this ItemDescription.
     * @param separator
     *   used between each words.
     * @return
     *   the description.
     */
    def mkString(separator: String = " "): String = ItemDescription.mkString(self, separator)

    /**
     * Used mkString with default separation.
     * @return
     *   The description of this ItemDescription.
     */
    def mkString: String = this.mkString()
  }
}
