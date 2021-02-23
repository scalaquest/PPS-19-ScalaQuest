package io.github.scalaquest.core.model.behaviorBased.commons.itemBehaviors.impl

import io.github.scalaquest.core.model.ItemRef
import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.commons.actioning.CActions.Open
import io.github.scalaquest.core.model.behaviorBased.commons.pushing.CMessagesExt
import io.github.scalaquest.core.model.behaviorBased.commons.reactions.CReactionsExt
import io.github.scalaquest.core.model.behaviorBased.simple.impl.StateUtilsExt

/**
 * The trait makes possible to add into the [[BehaviorBasedModel]] the <b>Container</b> behavior.
 */
trait ContainerExt
  extends BehaviorBasedModel
  with CMessagesExt
  with CReactionsExt
  with StateUtilsExt
  with OpenableExt {

  /**
   * An <b>ItemBehavior</b> that enables the possibility to open a <b>BehaviorBasedItem</b>, and to
   * reveal a set of items into the location when opened.
   */
  abstract class Container extends ItemBehavior {

    /**
     * <b>BehaviorBasedItem</b>s that will be added to the location after opening the subject.
     * @param state
     *   The actual game state. Used to extract the actual <b>BehaviorBasedItem</b>s from their
     *   refs.
     * @return
     *   <b>BehaviorBasedItem</b>s that will be added to the location after opening the subject.
     */
    def items(implicit state: S): Set[I]

    /**
     * <b>Reaction</b> triggered when the subject is opened: it reveals a set of
     * <b>BehaviorBasedItem</b>s into the current Room when opened.
     * @return
     *   <b>Reaction</b> triggered when the subject is opened.
     */
    def revealItems: Reaction

    /**
     * The <b>Openable</b> behavior associated to the subject.
     * @return
     *   The <b>Openable</b> behavior associated to the subject.
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
   * The standard implementation for <b>Container</b>.
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
        _  <- CReactions.modifyLocationItems(_ ++ items(s1).map(_.ref))
        s2 <- Reaction.messages(CMessages.ReversedIntoLocation(items(s1)))
      } yield s2
  }

  /**
   * A companion object used to create <b>Container</b> builders.
   */
  object Container {

    /**
     * A builder for a container that does not require a <b>Key</b> to be opened.
     * @param items
     *   Items that will be added to the location after opening the subject.
     * @return
     *   A builder for a container that does not require a Key to be opened.
     */
    def unlockedBuilder(
      items: Set[I]
    ): I => Container = i => SimpleContainer(items.map(_.ref), Openable.unlockedBuilder()(i))(i)

    /**
     * A builder for a container that requires a <b>Key</b> to be opened.
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
