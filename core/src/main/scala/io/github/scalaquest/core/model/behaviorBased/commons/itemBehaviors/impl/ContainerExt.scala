package io.github.scalaquest.core.model.behaviorBased.commons.itemBehaviors.impl

import io.github.scalaquest.core.model.ItemRef
import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.commons.actioning.CommonActions.Open
import io.github.scalaquest.core.model.behaviorBased.commons.pushing.CommonMessagesExt
import io.github.scalaquest.core.model.behaviorBased.commons.reactions.CommonReactionsExt

/**
 * A mixable trait that add the container behavior. This behavior allow to show some hidden
 * [[BehaviorBasedModel.ItemBehavior]] when the container item is opened.
 */
trait ContainerExt
  extends BehaviorBasedModel
  with CommonMessagesExt
  with CommonReactionsExt
  with OpenableExt {

  /**
   * The container base.
   */
  abstract class Container extends ItemBehavior {

    /**
     * The hidden items that have to be added to the current player location.
     * @param state
     *   the actual game state.
     * @return
     *   the revelead items.
     */
    def items(implicit state: S): Set[I]

    /**
     * @return
     *   a Reaction that reveal the Items.
     */
    def revealItems: Reaction

    /**
     * @return
     *   an openable behavior.
     */
    def openable: Openable

    /**
     * @return
     *   true if the container is open, false otherwise.
     */
    def isOpen: Boolean
  }

  /**
   * Simple implementation for a Container.
   * @param itemRefs
   *   the item reference to all the hidden items contained.
   * @param openable
   *   an [[Openable]] behavior.
   * @param subject
   *   the item that have the Container behavior.
   */
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
      s =>
        Reaction.combine(
          openable.open,
          Reactions.revealItems(items(s))
        )(s)
  }

  /**
   * A companion object used to create container builders that to be opened need the right key or a
   * container without this feature.
   */
  object Container {

    /**
     * A container builder openable without a key.
     * @param items
     *   the items contained into the container.
     * @return
     *   a builder for a container giving him an extra item.
     */
    def unlockedBuilder(
      items: Set[I]
    ): I => Container = i => SimpleContainer(items.map(_.ref), Openable.unlockedBuilder()(i))(i)

    /**
     * A container builder that could be open only with a specific key.
     * @param items
     *   the items contained into the container.
     * @return
     *   a builder for a container giving him an extra item.
     */
    def lockedBuilder(
      items: Set[I],
      key: Key
    ): I => Container = i => SimpleContainer(items.map(_.ref), Openable.lockedBuilder(key)(i))(i)
  }
}
