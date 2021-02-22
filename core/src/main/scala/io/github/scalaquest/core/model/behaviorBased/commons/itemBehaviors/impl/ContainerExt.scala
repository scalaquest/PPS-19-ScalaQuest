package io.github.scalaquest.core.model.behaviorBased.commons.itemBehaviors.impl

import io.github.scalaquest.core.model.ItemRef
import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.commons.actioning.CommonActions.Open
import io.github.scalaquest.core.model.behaviorBased.commons.pushing.CommonMessagesExt
import io.github.scalaquest.core.model.behaviorBased.commons.reactions.CommonReactionsExt
import io.github.scalaquest.core.model.behaviorBased.simple.impl.StateUtilsExt

trait ContainerExt
  extends BehaviorBasedModel
  with CommonMessagesExt
  with CommonReactionsExt
  with StateUtilsExt
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
  )(implicit val subject: I)
    extends Container
    with Delegate {

    override def delegateTriggers: ItemTriggers = openable.triggers

    override def receiverTriggers: ItemTriggers = {
      case (Open, maybeKey, s)
          if s.isInLocation(subject) && openable.canBeOpened(maybeKey)(s)
            && !openable.isOpen =>
        revealItems
    }

    override def items(implicit state: S): Set[I] = itemRefs.map(state.items(_))

    override def isOpen: Boolean = openable.isOpen

    override def revealItems: Reaction =
      for {
        s1 <- openable.open
        _  <- Reactions.modifyLocationItems(_ ++ items(s1).map(_.ref))
        s2 <- Reaction.messages(Messages.ReversedIntoLocation(items(s1)))
      } yield s2
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
