package io.github.scalaquest.core.model.behaviorBased.commons.reactions.impl

import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.commons.items.impl.KeyExt
import io.github.scalaquest.core.model.behaviorBased.commons.pushing.CommonMessagesExt
import io.github.scalaquest.core.model.behaviorBased.simple.impl.ReactionUtilsExt

/**
 * A Mixable trait that add a Reaction generated when a player open an openable Item.
 */
private[reactions] trait OpenExt
  extends BehaviorBasedModel
  with KeyExt
  with CommonMessagesExt
  with ReactionUtilsExt {

  /**
   * Open a specific Item.
   * @param itemToOpen
   *   is the item that have to be opened.
   * @param requiredKey
   *   if present is the requiredKey to open the item, otherwise key isn't necessary.
   * @return
   */
  private[reactions] def open(
    itemToOpen: I,
    requiredKey: Option[Key]
  ): Reaction =
    Reaction(
      requiredKey.collect { case SimpleKey(_, keyRef, true, _) =>
        Update(
          (locationRoomLens composeLens roomItemsLens).modify(_ - keyRef),
          bagLens.modify(_ - keyRef)
        )

      } getOrElse Update.empty,
      Messages.Opened(itemToOpen)
    )
}
