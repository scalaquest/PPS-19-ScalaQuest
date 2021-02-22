package io.github.scalaquest.core.model.behaviorBased.commons.itemBehaviors.impl

import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.commons.actioning.CommonActions.Take
import io.github.scalaquest.core.model.behaviorBased.commons.pushing.CommonMessagesExt
import io.github.scalaquest.core.model.behaviorBased.commons.reactions.CommonReactionsExt

/**
 * The trait makes possible to mix into the [[BehaviorBasedModel]] the Takeable behavior.
 */
trait TakeableExt extends BehaviorBasedModel with CommonMessagesExt with CommonReactionsExt {

  /**
   * A [[ItemBehavior]] associated to an [[Item]] that can be taken and put away into the bag of the
   * player.
   */
  abstract class Takeable extends ItemBehavior {

    /**
     * The take [[Reaction]].
     * @return
     *   the take [[Reaction]].
     */
    def take: Reaction
  }

  /**
   * Standard implementation of the Takeable.
   *
   * The behavior of an Item that could be put into the Bag of the player from the current room.
   * @param onTakeExtra
   *   Reaction to be executed into the State when taken, after the standard Reaction. It can be
   *   omitted.
   */
  case class SimpleTakeable(onTakeExtra: Reaction = Reaction.empty)(implicit subject: I)
    extends Takeable {

    override def triggers: ItemTriggers = {
      case (Take, item, None, state) if state.isInLocation(item) => take
    }

    /**
     * Returns a Reaction that removes the item from the current room, put it into the bag, executes
     * the eventual extra reaction.
     * @return
     *   A Reaction that removes the item from the current room, put it into the bag, executes the
     *   eventual extra reaction.
     */
    override def take: Reaction =
      Reaction.combine(
        Reactions.take(subject),
        onTakeExtra
      )
  }

  /**
   * Companion object for [[Takeable]]. Shortcut for the standard implementation.
   */
  object Takeable {

    def builder(onTakeExtra: Reaction = Reaction.empty): I => Takeable =
      SimpleTakeable(onTakeExtra)(_)
  }
}
