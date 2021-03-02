package io.github.scalaquest.core.model.behaviorBased.commons.items.impl

import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.commons.itemBehaviors.impl.GenericItemBehaviorExt
import io.github.scalaquest.core.model.{ItemDescription, ItemRef}

/**
 * The trait makes possible to mix into a [[BehaviorBasedModel]] the <b>GenericItem</b>.
 */
trait GenericItemExt extends BehaviorBasedModel with GenericItemBehaviorExt {

  /**
   * A standard <b>BehaviorBasedItem</b> completely and freely configurable, without a specific
   * category.
   */
  trait GenericItem extends BehaviorBasedItem

  /**
   * Standard implementation of <b>GenericItem</b>.
   *
   * @param description
   *   An [[ItemDescription]] for the item.
   * @param ref
   *   A unique reference to the item.
   * @param behaviorsBuilders
   *   All the behaviors associated to the item.
   */
  case class SimpleGenericItem(
    description: ItemDescription,
    ref: ItemRef,
    behaviorsBuilders: Seq[I => ItemBehavior] = Seq.empty
  ) extends GenericItem {
    override val behaviors: Seq[ItemBehavior] = behaviorsBuilders.map(_(this))
  }

  /**
   * Companion object for <b>GenericItem</b>.
   */
  object GenericItem {

    /**
     * A standard <b>GenericItem</b>.
     * @param description
     *   An [[ItemDescription]] for the item.
     * @param extraBehavBuilders
     *   Builder of all the behaviors associated to the item.
     * @return
     *   An instance of a standard <b>GenericItem</b>.
     */
    def apply(
      description: ItemDescription,
      extraBehavBuilders: Seq[I => ItemBehavior] = Seq.empty
    ): GenericItem = SimpleGenericItem(description, ItemRef(description), extraBehavBuilders)

    /**
     * A <b>GenericItem</b> with a single behavior.
     * @param description
     *   An [[ItemDescription]] for the item.
     * @param behavior
     *   Builder for the single behavior associated to the item.
     * @return
     *   An instance of a <b>GenericItem</b> with a single behavior.
     */
    def withSingleBehavior(
      description: ItemDescription,
      behavior: I => ItemBehavior
    ): GenericItem = SimpleGenericItem(description, ItemRef(description), Seq(behavior))

    /**
     * An item with no behaviors.
     * @param description
     *   An [[ItemDescription]] for the item.
     * @return
     *   An item with no behaviors.
     */
    def empty(
      description: ItemDescription
    ): GenericItem = SimpleGenericItem(description, ItemRef(description), Seq())

    /**
     * A <b>GenericItem</b> with a single behavior, created on-the-fly by some <b>ItemTriggers</b>.
     * @param description
     *   the generic item description.
     * @param behaviorTriggers
     *   Triggers for the single behavior associated to the item.
     * @return
     *   An instance of a <b>GenericItem</b> with a single behavior.
     */
    def withGenBehavior(
      description: ItemDescription,
      behaviorTriggers: ItemTriggers
    ): GenericItem =
      SimpleGenericItem(
        description,
        ItemRef(description),
        Seq(GenericItemBehavior.builder(behaviorTriggers))
      )
  }
}
