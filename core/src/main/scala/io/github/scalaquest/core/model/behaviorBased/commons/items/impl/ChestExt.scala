package io.github.scalaquest.core.model.behaviorBased.commons.items.impl

import io.github.scalaquest.core.model.{ItemDescription, ItemRef}
import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.commons.itemBehaviors.impl.ContainerExt

/**
 * The trait makes possible to mix into a [[BehaviorBasedModel]] the <b>Chest BehaviorBasedItem</b>.
 */
trait ChestExt extends BehaviorBasedModel with ContainerExt {

  /**
   * A <b>BehaviorBasedItem</b> that can be opened, and reveals a set of items when opened.
   */
  trait Chest extends BehaviorBasedItem {

    /**
     * The "openness" state of the item, as a boolean value. Initially closed.
     * @return
     *   True if item is already opened, False otherwise.
     */
    def isOpen: Boolean

    /**
     * A <b>Chest</b> has always an associated <b>Container</b> behavior, that can be referenced
     * from here.
     * @return
     *   The <b>Container</b> behavior of the item.
     */
    def container: Container
  }

  /**
   * A standard implementation for <b>Chest</b>.
   * @param description
   *   An [[ItemDescription]] for the item.
   * @param ref
   *   A unique reference to the item.
   * @param containerBuilder
   *   A builder for the <b>Container</b> behavior associated to the item.
   * @param extraBehavBuilders
   *   Additional behaviors associated to the item.
   */
  case class SimpleChest(
    description: ItemDescription,
    ref: ItemRef,
    containerBuilder: I => Container,
    extraBehavBuilders: Seq[I => ItemBehavior] = Seq.empty
  ) extends Chest {
    override val container: Container         = containerBuilder(this)
    override val behaviors: Seq[ItemBehavior] = container +: extraBehavBuilders.map(_(this))
    override def isOpen: Boolean              = container.isOpen
  }

  /**
   * Companion object for <b>Chest</b>.
   */
  object Chest {

    /**
     * Creates a standard <b>Chest</b>.
     * @param description
     *   An [[ItemDescription]] for the item.
     * @param containerBuilder
     *   A builder for the <b>Container</b> behavior associated to the item.
     * @param extraBehavBuilder
     *   Additional behaviors associated to the item.
     * @return
     *   An instance of a standard <b>Chest</b>.
     */
    def apply(
      description: ItemDescription,
      containerBuilder: I => Container,
      extraBehavBuilder: Seq[I => ItemBehavior] = Seq.empty
    ): Chest = SimpleChest(description, ItemRef(description), containerBuilder, extraBehavBuilder)

    /**
     * A chest that to be opened doesn't need any key.
     * @param description
     *   the [[ItemDescription]] of the chest.
     * @param items
     *   the items contained in a chest
     * @param extraBehavBuilder
     *   possible extra behavior when is open the container item.
     * @return
     *   an instance that implements chest trait.
     */
    def createUnlocked(
      description: ItemDescription,
      items: Set[I] = Set.empty,
      extraBehavBuilder: Seq[I => ItemBehavior] = Seq.empty
    ): Chest =
      apply(
        description,
        Container.unlockedBuilder(items),
        extraBehavBuilder
      )

    /**
     * A chest that to be opened require a key.
     * @param description
     *   the [[ItemDescription]] of the chest.
     * @param items
     *   the items contained in a chest.
     * @param key
     *   the key required to open the chest.
     * @param extraBehavBuilder
     *   possible extra behavior when is open the container item.
     * @return
     *   an instance that implements chest trait.
     * @return
     */
    def createLocked(
      description: ItemDescription,
      items: Set[I] = Set.empty,
      key: Key,
      extraBehavBuilder: Seq[I => ItemBehavior] = Seq.empty
    ): Chest =
      apply(
        description,
        Container.lockedBuilder(items, key),
        extraBehavBuilder
      )
  }
}
