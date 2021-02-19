package io.github.scalaquest.core.model.behaviorBased.commons.items.impl

import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.{ItemDescription, ItemRef}

/**
 * The trait makes possible to mix into a [[BehaviorBasedModel]] the Key Item.
 */
trait KeyExt extends BehaviorBasedModel {

  /**
   * A [[BehaviorBasedItem]] that should be used to open/close items with a Openable behavior.
   */
  trait Key extends BehaviorBasedItem {
    def disposable: Boolean
  }

  /**
   * Standard implementation of the common [[Key]].
   */
  case class SimpleKey(
    description: ItemDescription,
    ref: ItemRef,
    disposable: Boolean = true,
    extraBehavBuilders: Seq[I => ItemBehavior] = Seq.empty
  ) extends Key {
    override val behaviors: Seq[ItemBehavior] = extraBehavBuilders.map(_(this))
  }

  /**
   * Companion object for [[Key]]. Makes the initialization more succinct.
   */
  object Key {

    def apply(
      description: ItemDescription,
      disposable: Boolean = true,
      extraBehavBuilders: Seq[I => ItemBehavior] = Seq.empty
    ): Key = SimpleKey(description, ItemRef(description), disposable, extraBehavBuilders)
  }
}
