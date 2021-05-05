/*
 * Copyright (c) 2021 ScalaQuest Team.
 *
 * This file is part of ScalaQuest, and is distributed under the terms of the MIT License as
 * described in the file LICENSE in the ScalaQuest distribution's top directory.
 */

package io.github.scalaquest.core.model.behaviorBased.commons.groundBehaviors.impl

import io.github.scalaquest.core.model.behaviorBased.BehaviorBasedModel
import io.github.scalaquest.core.model.behaviorBased.commons.actioning.CActions.InspectBag
import io.github.scalaquest.core.model.behaviorBased.commons.pushing.CMessagesExt
import io.github.scalaquest.core.model.behaviorBased.commons.reactions.CReactionsExt

/**
 * The trait makes possible to mix into a [[BehaviorBasedModel]] the <b>InspectableBag
 * GroundBehavior</b>.
 */
trait InspectableBagExt extends BehaviorBasedModel with CMessagesExt with CReactionsExt {

  /**
   * A <b>GroundBehavior</b> that enables the possibility to know what items are contained into the
   * bag.
   */
  abstract class InspectableBag extends GroundBehavior {

    /**
     * A <b>Reaction</b> that communicates to the user the items contained into the bag.
     * @return
     *   A <b>Reaction</b> that communicates to the user the items contained into the bag.
     */
    def inspectBag: Reaction
  }

  /**
   * A standard implementation for <b>InspectableBag</b>.
   *
   * @param onInspectExtra
   *   <b>Reaction</b> to be executed when the player successfully inspects the bag. It can be
   *   omitted.
   */
  case class SimpleInspectableBag(onInspectExtra: Reaction = Reaction.empty)
    extends InspectableBag {

    override def triggers: GroundTriggers = { case (InspectBag, _) => inspectBag }

    override def inspectBag: Reaction =
      for {
        s1 <- Reaction.empty
        _  <- CReactions.addMessage(CMessages.InspectedBag(s1.bag))
        s2 <- onInspectExtra
      } yield s2
  }

  /**
   * Companion object for <b>InspectableBag</b>.
   */
  object InspectableBag {

    /**
     * Shortcut for the standard implementation.
     * @param onInspectExtra
     *   An additional <b>Reaction</b> generated when user inspects the bag.
     * @return
     *   An instance of <b>SimpleInspectableBag</b>.
     */
    def apply(onInspectExtra: Reaction = Reaction.empty): InspectableBag =
      SimpleInspectableBag(onInspectExtra)
  }
}
