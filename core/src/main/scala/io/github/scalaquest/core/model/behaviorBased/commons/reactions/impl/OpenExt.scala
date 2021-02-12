package io.github.scalaquest.core.model.behaviorBased.commons.reactions.impl

import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.commons.items.impl.KeyExt
import io.github.scalaquest.core.model.behaviorBased.commons.pushing.CommonMessagesExt
import io.github.scalaquest.core.model.behaviorBased.simple.impl.StateUtilsExt

trait OpenExt extends BehaviorBasedModel with KeyExt with StateUtilsExt with CommonMessagesExt {

  private[reactions] def open(
    itemToOpen: I,
    requiredKey: Option[Key],
    iskeyConsumable: Boolean
  ): Reaction =
    state => {

      val modState = if (iskeyConsumable) {
        val keyConsumedState: S =
          requiredKey.fold(state)(k => {
            val newLoc = roomItemsLens.modify(_ - k.ref)(state.location)
            state.applyReactions(
              roomsLens.modify(_ + (newLoc.ref -> newLoc)),
              bagLens.modify(_ - k.ref)
            )
          })
        keyConsumedState
      } else {
        state
      }

      modState.applyReactions(
        messageLens.modify(_ :+ Opened(itemToOpen))
      )

    }
}
