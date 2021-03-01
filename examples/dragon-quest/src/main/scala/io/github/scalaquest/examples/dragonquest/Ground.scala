package io.github.scalaquest.examples.dragonquest

import io.github.scalaquest.core.model.Direction
import io.github.scalaquest.core.model.behaviorBased.commons.actioning.CActions
import model.{
  BehaviorBasedGround,
  CGround,
  GroundBehavior,
  CReactions,
  Reaction,
  roomsLens,
  roomItemsLens
}

object Ground {

  var isInitState = true

  def ground: BehaviorBasedGround =
    CGround(
      Seq(
        new GroundBehavior {

          override def triggers: model.GroundTriggers = {
            case (CActions.Go(Direction.East), _) if isInitState =>
              isInitState = false
              val updatedRoom = roomItemsLens.modify(_ + Items.basilisk.ref)(Geography.tunnel)
              for {
                _ <- CReactions.modifyLocationItems(_ - Items.basilisk.ref)
                _ <- Reaction(roomsLens.modify(_ + (Geography.tunnel.ref -> updatedRoom)))
                s <- Reaction.messages(Pusher.BasiliskMovedToTunnel)
              } yield s
          }
        }
      )
    )
}
