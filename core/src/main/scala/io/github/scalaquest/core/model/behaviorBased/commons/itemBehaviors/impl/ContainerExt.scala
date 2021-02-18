package io.github.scalaquest.core.model.behaviorBased.commons.itemBehaviors.impl

import io.github.scalaquest.core.model.ItemRef
import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.commons.actioning.CommonActions.Open
import io.github.scalaquest.core.model.behaviorBased.commons.pushing.CommonMessagesExt
import io.github.scalaquest.core.model.behaviorBased.commons.reactions.CommonReactionsExt

trait ContainerExt
  extends BehaviorBasedModel
  with CommonMessagesExt
  with CommonReactionsExt
  with OpenableExt {

  abstract class Container extends ItemBehavior {
    def items(implicit state: S): Set[I]
    def revealItems: Reaction
    def openable: Openable
    def isOpen: Boolean
  }

  case class SimpleContainer(
    itemRefs: Set[ItemRef],
    openable: Openable
  )(implicit subject: I)
    extends Container
    with Delegate {

    override def delegateTriggers: ItemTriggers = openable.triggers

    override def receiverTriggers: ItemTriggers = {
      case (Open, _, maybeKey, s)
          if s.isInLocation(subject) && openable.canBeOpened(maybeKey)(s)
            && !openable.isOpen =>
        revealItems
    }

    override def items(implicit state: S): Set[I] = itemRefs.map(state.items(_))

    override def isOpen: Boolean = openable.isOpen

    override def revealItems: Reaction =
      Reaction.combine(
        openable.open,
        Reaction(
          (locationRoomLens composeLens roomItemsLens).modify(_ ++ itemRefs)
        )
      )
  }

  object Container {

    def unlockedBuilder(
      items: Set[I]
    ): I => Container = i => SimpleContainer(items.map(_.ref), Openable.unlockedBuilder()(i))(i)

    def lockedBuilder(
      items: Set[I],
      key: Key
    ): I => Container = i => SimpleContainer(items.map(_.ref), Openable.lockedBuilder(key)(i))(i)
  }
}
