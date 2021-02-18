package io.github.scalaquest.core.model.behaviorBased.commons.reactions.impl

import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.commons.items.impl.KeyExt
import io.github.scalaquest.core.model.behaviorBased.commons.pushing.CommonMessagesExt
import io.github.scalaquest.core.model.behaviorBased.simple.impl.{ReactionUtilsExt, StateUtilsExt}

/**
 * A Reaction generated when a player open an openable Item.
 */
private[reactions] trait OpenExt
  extends BehaviorBasedModel
  with KeyExt
  with StateUtilsExt
  with CommonMessagesExt
  with ReactionUtilsExt {

  private[reactions] def open(
    itemToOpen: I,
    requiredKey: Option[Key],
    iskeyConsumable: Boolean
  ): Reaction = {
    val optReaction = requiredKey.map(k =>
      Reaction(
        Update(
          (locationRoomLens composeLens roomItemsLens).modify(_ - k.ref),
          bagLens.modify(_ - k.ref)
        )
      )
    )
    val reaction =
      if (iskeyConsumable) optReaction.getOrElse(Reaction.empty)
      else Reaction.empty
    Reaction.appendMessage(Messages.Opened(itemToOpen))(reaction)
  }

}
