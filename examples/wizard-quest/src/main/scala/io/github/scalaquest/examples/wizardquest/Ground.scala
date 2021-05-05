/*
 * Copyright (c) 2021 ScalaQuest Team.
 *
 * This file is part of ScalaQuest, and is distributed under the terms of the MIT License as
 * described in the file LICENSE in the ScalaQuest distribution's top directory.
 */

package io.github.scalaquest.examples.wizardquest

import io.github.scalaquest.core.model.Direction
import io.github.scalaquest.core.model.behaviorBased.commons.actioning.CActions
import model.{BehaviorBasedGround, CGround, CReactions}

/**
 * A custom ground for the example.
 */
object Ground {

  def ground: BehaviorBasedGround =
    CGround.withGenExtraBehavior({
      case (CActions.Go(Direction.East), _) if Reactions.isInitState =>
        Reactions.isInitState = false

        for {
          _ <- CReactions.modifyLocationItems(_ - Items.basilisk.ref)
          _ <- CReactions.modifyRoomItems(Geography.tunnel.ref, _ + Items.basilisk.ref)
          _ <- CReactions.switchLocation(Geography.tunnel)
          s <- CReactions.addMessage(Messages.EscapingFromBasilisk)
        } yield s
    })
}
