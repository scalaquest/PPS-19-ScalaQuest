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
  trait Key extends BehaviorBasedItem

  /**
   * Standard implementation of the common [[Key]].
   */
  case class SimpleKey(
    description: ItemDescription,
    ref: ItemRef,
    additionalBehaviors: ItemBehavior*
  ) extends Key {
    override val behaviors: Seq[ItemBehavior] = additionalBehaviors
  }

  /**
   * Companion object for [[Key]]. Makes the initialization more succinct.
   */
  object Key {

    def apply(
      description: ItemDescription,
      additionalBehaviors: Seq[ItemBehavior] = Seq.empty
    ): Key = SimpleKey(description, ItemRef(description), additionalBehaviors: _*)
  }
}
