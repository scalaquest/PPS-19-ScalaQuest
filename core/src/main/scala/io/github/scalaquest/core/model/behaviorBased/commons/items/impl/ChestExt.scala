package io.github.scalaquest.core.model.behaviorBased.commons.items.impl

import io.github.scalaquest.core.model.{ItemDescription, ItemRef}
import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.commons.itemBehaviors.impl.ContainerExt

/**
 * A mixable trait that add an implementation for [[ContainerExt.Container]] behavior.
 */
trait ChestExt extends BehaviorBasedModel with ContainerExt {

  /**
   * The Chest interface.
   */
  trait Chest extends BehaviorBasedItem {

    /**
     * Control if chest is already opened
     * @return
     *   true if chest is already opened, false otherwise.
     */
    def isOpen: Boolean

    /**
     * @return
     *   the specific container behavior.
     */
    def container: Container
  }

  /**
   * A Simple implementation for [[Chest]].
   * @param description
   *   the chest item description.
   * @param ref
   *   the unique reference to the item.
   * @param containerBuilder
   *   the container builder.
   * @param extraBehavBuilders
   *   extra behavior on opening the item.
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
   * Companion object with useful methods in order to facilitates the creation of a [[SimpleChest]].
   */
  object Chest {

    /**
     * Create a SimpleChest.
     * @param description
     *   the [[ItemDescription]] of the chest.
     * @param containerBuilder
     *   the container builder.
     * @param extraBehavBuilder
     *   possible extra behavior when is open the container item.
     * @return
     *   an instance of SimpleChest.
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
