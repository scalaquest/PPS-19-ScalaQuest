/*
 * Copyright (c) 2021 ScalaQuest Team.
 *
 * This file is part of ScalaQuest, and is distributed under the terms of the MIT License as
 * described in the file LICENSE in the ScalaQuest distribution's top directory.
 */

package io.github.scalaquest.core.model.behaviorBased.commons.items.impl

import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.commons.itemBehaviors.impl.EatableExt
import io.github.scalaquest.core.model.{ItemDescription, ItemRef}

/**
 * The trait makes possible to mix into a [[BehaviorBasedModel]] the <b>Food BehaviorBasedItem</b>.
 */
trait FoodExt extends BehaviorBasedModel with EatableExt {

  /**
   * A <b>BehaviorBasedItem</b> that can be eaten by the player.
   */
  trait Food extends BehaviorBasedItem {

    /**
     * A <b>Food</b> has always an associated <b>Eatable</b> behavior, that can be referenced from
     * here.
     * @return
     *   The <b>Eatable</b> behavior of the item.
     */
    def eatable: Eatable
  }

  /**
   * Standard implementation of <b>Food</b>.
   *
   * @param description
   *   An [[ItemDescription]] for the item.
   * @param ref
   *   A unique reference to the item.
   * @param eatableBuilder
   *   A builder for the <b>Eatable</b> behavior associated to the item.
   * @param extraBehavBuilders
   *   Additional behaviors associated to the item.
   */
  case class SimpleFood(
    description: ItemDescription,
    ref: ItemRef,
    eatableBuilder: I => Eatable,
    extraBehavBuilders: Seq[I => ItemBehavior] = Seq.empty
  ) extends Food {
    final override val eatable: Eatable       = eatableBuilder(this)
    override val behaviors: Seq[ItemBehavior] = eatable +: extraBehavBuilders.map(_(this))
  }

  /**
   * Companion object for <b>Food</b>.
   */
  object Food {

    /**
     * Creates a standard <b>Food</b>.
     * @param description
     *   An [[ItemDescription]] for the item.
     * @param onEatExtra
     *   An extra <b>Reaction</b> to be triggered after eating.
     * @param extraBehavBuilders
     *   Additional behaviors associated to the item.
     * @return
     *   An instance of a standard <b>Door</b>.
     */
    def apply(
      description: ItemDescription,
      onEatExtra: Reaction = Reaction.empty,
      extraBehavBuilders: Seq[I => ItemBehavior] = Seq.empty
    ): Food =
      SimpleFood(description, ItemRef(description), Eatable.builder(onEatExtra), extraBehavBuilders)

  }
}
