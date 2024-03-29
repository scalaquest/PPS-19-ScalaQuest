/*
 * Copyright (c) 2021 ScalaQuest Team.
 *
 * This file is part of ScalaQuest, and is distributed under the terms of the MIT License as
 * described in the file LICENSE in the ScalaQuest distribution's top directory.
 */

package io.github.scalaquest.core.model.behaviorBased.commons.itemBehaviors.impl

import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.commons.actioning.CActions.Take
import io.github.scalaquest.core.model.behaviorBased.commons.pushing.CMessagesExt
import io.github.scalaquest.core.model.behaviorBased.commons.reactions.CReactionsExt
import io.github.scalaquest.core.model.behaviorBased.simple.impl.StateUtilsExt

/**
 * The trait makes possible to add into the [[BehaviorBasedModel]] the <b>Takeable</b> behavior.
 */
trait TakeableExt
  extends BehaviorBasedModel
  with CMessagesExt
  with CReactionsExt
  with StateUtilsExt {

  /**
   * An <b>ItemBehavior</b> aassociated to a <b>BehaviorBasedItem</b> that can be taken and put away
   * into the bag of the player.
   */
  abstract class Takeable extends ItemBehavior {

    /**
     * A <b>Reaction</b> that moves the subject from the location to the bag.
     * @return
     *   A <b>Reaction</b> that moves the subject from the location to the bag.
     */
    def take: Reaction
  }

  /**
   * Standard implementation of <b>Takeable</b>.
   *
   * @param onTakeExtra
   *   <b>Reaction</b> to be executed after after the standard take <b>Reaction</b>. It can be
   *   omitted.
   */
  case class SimpleTakeable(onTakeExtra: Reaction = Reaction.empty)(implicit val subject: I)
    extends Takeable {

    override def triggers: ItemTriggers = {
      case (Take, None, state) if state.isInLocation(subject) => take
    }

    override def take: Reaction =
      for {
        _ <- CReactions.modifyLocationItems(_ - subject.ref)
        _ <- CReactions.modifyBag(_ + subject.ref)
        _ <- CReactions.addMessage(CMessages.Taken(subject))
        s <- onTakeExtra
      } yield s
  }

  /**
   * Companion object for <b>Takeable</b>.
   */
  object Takeable {

    /**
     * Builder that returns a standard <b>Takeable</b> behavior given a subject.
     *
     * @param onTakeExtra
     *   <b>Reaction</b> to be executed after after the standard take <b>Reaction</b>. It can be
     *   omitted.
     * @return
     *   Builder that returns a standard <b>Takeable</b> behavior given a subject.
     */
    def builder(onTakeExtra: Reaction = Reaction.empty): I => Takeable =
      SimpleTakeable(onTakeExtra)(_)
  }
}
