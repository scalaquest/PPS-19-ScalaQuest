/*
 * Copyright (c) 2021 ScalaQuest Team.
 *
 * This file is part of ScalaQuest, and is distributed under the terms of the MIT License as
 * described in the file LICENSE in the ScalaQuest distribution's top directory.
 */

package io.github.scalaquest.core.model.behaviorBased.commons.itemBehaviors.impl

import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.commons.actioning.CActions.Eat
import io.github.scalaquest.core.model.behaviorBased.commons.pushing.CMessagesExt
import io.github.scalaquest.core.model.behaviorBased.commons.reactions.CReactionsExt
import io.github.scalaquest.core.model.behaviorBased.simple.impl.StateUtilsExt

/**
 * The trait makes possible to add into the [[BehaviorBasedModel]] the <b>Eatable</b> Behavior.
 */
trait EatableExt
  extends BehaviorBasedModel
  with CMessagesExt
  with CReactionsExt
  with StateUtilsExt {

  /**
   * An <b>ItemBehavior</b> associated to an <b>BehaviorBasedItem</b> that can be eaten. After an
   * item is eaten, it should be removed from the player bag (or from the current room, if it was
   * there).
   */
  abstract class Eatable extends ItemBehavior {

    /**
     * A Reaction that should remove the Item from the player bag (or from the location).
     * @return
     *   A Reaction that should remove the Item from the player bag (or from the location).
     */
    def eat: Reaction
  }

  /**
   * Standard implementation of the Eatable behavior.
   *
   * This is a behavior associated to an Item that can be eaten, if present in the bag or in the
   * room.
   * @param onEatExtra
   *   Reaction to be executed when the item has been successfully eaten, after the standard
   *   <b>Reaction</b>. It can be omitted.
   */
  case class SimpleEatable(onEatExtra: Reaction = Reaction.empty)(implicit val subject: I)
    extends Eatable {

    override def triggers: ItemTriggers = {
      case (Eat, None, state) if state.isInScope(subject) => eat
    }

    override def eat: Reaction =
      for {
        _ <- CReactions.modifyLocationItems(_ - subject.ref)
        _ <- CReactions.modifyBag(_ - subject.ref)
        _ <- CReactions.addMessage(CMessages.Eaten(subject))
        s <- onEatExtra
      } yield s
  }

  /**
   * Companion object for [[Eatable]].
   */
  object Eatable {

    /**
     * A function that builds an Eatable behavior given a subject Item.
     * @param onEatExtra
     *   Reaction to be executed when the item has been successfully eaten, after the standard
     *   Reaction. It can be omitted.
     * @return
     *   A function that builds an Eatable behavior given a subject Item.
     */
    def builder(onEatExtra: Reaction = Reaction.empty): I => Eatable = SimpleEatable(onEatExtra)(_)
  }
}
