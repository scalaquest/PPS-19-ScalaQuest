package io.github.scalaquest.core.model.behaviorBased.commons.items.impl

import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.{ItemDescription, ItemRef}

/**
 * The trait makes possible to mix into a [[BehaviorBasedModel]] the Generic Item.
 */
trait GenericItemExt extends BehaviorBasedModel {

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
    additionalBehaviors: ItemBehavior*
  ) extends GenericItem {
    override def behaviors: Seq[ItemBehavior] = additionalBehaviors
  }

  /**
   * Companion object for [[GenericItem]]. Makes the initialization more succinct.
   */
  object GenericItem {

    def apply(
      description: ItemDescription,
      additionalBehaviors: Seq[ItemBehavior] = Seq.empty
    ): GenericItem = SimpleGenericItem(description, ItemRef(description), additionalBehaviors: _*)
  }
}
