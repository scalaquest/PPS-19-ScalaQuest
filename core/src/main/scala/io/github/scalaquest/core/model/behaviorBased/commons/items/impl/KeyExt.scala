/*
 * Copyright (c) 2021 ScalaQuest Team.
 *
 * This file is part of ScalaQuest, and is distributed under the terms of the MIT License as
 * described in the file LICENSE in the ScalaQuest distribution's top directory.
 */

package io.github.scalaquest.core.model.behaviorBased.commons.items.impl

import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.{ItemDescription, ItemRef}

/**
 * The trait makes possible to mix into a [[BehaviorBasedModel]] the <b>Key BehaviorBasedItem</b>.
 */
trait KeyExt extends BehaviorBasedModel {

  /**
   * A <b>BehaviorBasedItem</b> that should be used to open/close items with an <b>Openable</b>
   * behavior.
   */
  trait Key extends BehaviorBasedItem {

    /**
     * A disposable key is cancelled from the game after being used to open something.
     * @return
     *   True if key is cancelled after his use, False otherwise.
     */
    def disposable: Boolean
  }

  /**
   * Standard implementation of <b>Key</b>.
   *
   * @param description
   *   An [[ItemDescription]] for the item.
   * @param ref
   *   A unique reference to the item.
   * @param extraBehavBuilders
   *   Additional behaviors associated to the item.
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
   * Companion object for <b>Key</b>.
   */
  object Key {

    /**
     * Creates a standard <b>Key</b>.
     * @param description
     *   An [[ItemDescription]] for the item.
     * @param extraBehavBuilders
     *   Additional behaviors associated to the item.
     * @return
     *   An instance of a standard <b>Key</b>.
     */
    def apply(
      description: ItemDescription,
      disposable: Boolean = true,
      extraBehavBuilders: Seq[I => ItemBehavior] = Seq.empty
    ): Key = SimpleKey(description, ItemRef(description), disposable, extraBehavBuilders)
  }
}
