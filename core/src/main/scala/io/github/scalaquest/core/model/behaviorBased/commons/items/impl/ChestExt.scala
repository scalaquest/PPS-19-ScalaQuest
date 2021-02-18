package io.github.scalaquest.core.model.behaviorBased.commons.items.impl

import io.github.scalaquest.core.model.{ItemDescription, ItemRef}
import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.commons.itemBehaviors.impl.ContainerExt

trait ChestExt extends BehaviorBasedModel with ContainerExt {

  trait Chest extends BehaviorBasedItem {
    def isOpen: Boolean
    def container: Container
  }

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

  object Chest {

    def apply(
      description: ItemDescription,
      containerBuilder: I => Container,
      extraBehavBuilder: Seq[I => ItemBehavior] = Seq.empty
    ): Chest = SimpleChest(description, ItemRef(description), containerBuilder, extraBehavBuilder)

    def createUnlocked(
      description: ItemDescription,
      items: Set[I] = Set.empty,
      extraBehavBuilder: Seq[I => ItemBehavior] = Seq.empty
    ): Chest =
      SimpleChest(
        description,
        ItemRef(description),
        Container.unlockedBuilder(items),
        extraBehavBuilder
      )

    def createLocked(
      description: ItemDescription,
      items: Set[I] = Set.empty,
      key: Key,
      extraBehavBuilder: Seq[I => ItemBehavior] = Seq.empty
    ): Chest =
      SimpleChest(
        description,
        ItemRef(description),
        Container.lockedBuilder(items, key),
        extraBehavBuilder
      )
  }
}
