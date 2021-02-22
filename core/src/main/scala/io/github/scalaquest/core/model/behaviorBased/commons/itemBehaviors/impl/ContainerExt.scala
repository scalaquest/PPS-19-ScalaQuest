package io.github.scalaquest.core.model.behaviorBased.commons.itemBehaviors.impl

import io.github.scalaquest.core.model.ItemRef
import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.commons.actioning.CommonActions.Open
import io.github.scalaquest.core.model.behaviorBased.commons.pushing.CommonMessagesExt
import io.github.scalaquest.core.model.behaviorBased.commons.reactions.CommonReactionsExt
import io.github.scalaquest.core.model.behaviorBased.simple.impl.StateUtilsExt

/**
 * A mixable trait that add the container behavior. This behavior allows an Item to be opened, and
 * to release a set of Items into the current Room when opened.
 */
trait ContainerExt
  extends BehaviorBasedModel
  with CommonMessagesExt
  with CommonReactionsExt
  with StateUtilsExt
  with OpenableExt {

  /**
   * An ItemBehavior that enables the possibility to open the item, and to reveal a set of Items
   * into the current Room when opened.
   */
  abstract class Container extends ItemBehavior {

    /**
     * Items that will be added to the location after opening the subject.
     * @param state
     *   The actual game state. Used to extract the actual Items from their refs.
     * @return
     *   Items that will be added to the location after opening the subject.
     */
    def items(implicit state: S): Set[I]

    /**
     * Reaction triggered when the Item is opened: it reveals a set of Items into the current Room
     * when opened.
     * @return
     *   Reaction triggered when the Item is opened.
     */
    def revealItems: Reaction

    /**
     * The Openable behavior associated to the Item.
     * @return
     *   The Openable behavior associated to the Item.
     */
    def openable: Openable

    /**
     * The "openness" state of the Item, initially closed.
     * @return
     *   True if the container is open, False otherwise.
     */
    def isOpen: Boolean
  }

  /**
   * The standard implementation for the Container.
   * @param itemRefs
   *   Refs to the Items that will be added to the location after opening the subject.
   * @param openable
   *   The Openable behavior associated to the subject Item.
   * @param subject
   *   The item that owes the Container behavior.
   */
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

  /**
   * A companion object used to create container builders that to be opened need the right key or a
   * container without this feature.
   */
  object Container {

    /**
     * A builder for a container that do not requires a Key to be opened.
     * @param items
     *   Items that will be added to the location after opening the subject.
     * @return
     *   A builder for a container that do not requires a Key to be opened.
     */
    def unlockedBuilder(
      items: Set[I]
    ): I => Container = i => SimpleContainer(items.map(_.ref), Openable.unlockedBuilder()(i))(i)

    /**
     * A builder for a container that requires a Key to be opened.
     * @param items
     *   Items that will be added to the location after opening the subject.
     * @param key
     *   The Key required to open the containerized item.
     * @return
     *   A builder for a container that requires a Key to be opened.
     */
    def lockedBuilder(
      items: Set[I],
      key: Key
    ): I => Container = i => SimpleContainer(items.map(_.ref), Openable.lockedBuilder(key)(i))(i)
  }
}
