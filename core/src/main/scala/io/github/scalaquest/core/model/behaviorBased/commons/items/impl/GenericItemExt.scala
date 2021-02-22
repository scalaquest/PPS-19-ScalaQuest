package io.github.scalaquest.core.model.behaviorBased.commons.items.impl

import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.commons.itemBehaviors.impl.GenericBehaviorExt
import io.github.scalaquest.core.model.{ItemDescription, ItemRef}

/**
 * The trait makes possible to mix into a [[BehaviorBasedModel]] the Generic Item.
 */
trait GenericItemExt extends BehaviorBasedModel with GenericBehaviorExt {

  /**
   * A standard [[BehaviorBasedItem]], completely and freely configurable, without a specific
   * category.
   */
  trait GenericItem extends BehaviorBasedItem

  /**
   * Standard implementation of the common [[GenericItem]].
   */
  case class SimpleGenericItem(
    description: ItemDescription,
    ref: ItemRef,
    extraBehavBuilders: Seq[I => ItemBehavior] = Seq.empty
  ) extends GenericItem {
    override val behaviors: Seq[ItemBehavior] = extraBehavBuilders.map(_(this))
  }

  /**
   * Companion object for [[GenericItem]]. Makes the initialization more succinct.
   */
  object GenericItem {

    /**
     * Facilitates the creation of a [[GenericItem]].
     * @param description
     *   the item's description.
     * @param extraBehavBuilders
     *   some possible extra behavior for the item.
     * @return
     *   the [[SimpleGenericItem]] instance.
     */
    def apply(
      description: ItemDescription,
      extraBehavBuilders: Seq[I => ItemBehavior] = Seq.empty
    ): GenericItem = SimpleGenericItem(description, ItemRef(description), extraBehavBuilders)

    /**
     * A generic item with only one behavior.
     * @param description
     *   the generic item description.
     * @param behavior
     *   the item behavior.
     * @return
     *   the [[SimpleGenericItem]] instance.
     */
    def withSingleBehavior(
      description: ItemDescription,
      behavior: I => ItemBehavior
    ): GenericItem = SimpleGenericItem(description, ItemRef(description), Seq(behavior))

    /**
     * A generic item with some behaviors triggers.
     * @param description
     *   the generic item description.
     * @param behaviorTriggers
     *   behavior triggers.
     * @return
     *   the [[SimpleGenericItem]] instance.
     */
    def withGenBehavior(
      description: ItemDescription,
      behaviorTriggers: ItemTriggers
    ): GenericItem =
      SimpleGenericItem(
        description,
        ItemRef(description),
        Seq(GenericBehavior.builder(behaviorTriggers))
      )
  }
}
