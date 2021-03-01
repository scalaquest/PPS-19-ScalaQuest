package io.github.scalaquest.examples.dragonquest

import io.github.scalaquest.core.model.Direction
import io.github.scalaquest.core.model.behaviorBased.commons.actioning.CActions
import io.github.scalaquest.core.model.behaviorBased.simple.SimpleModel.locationLens
import model.{
  BehaviorBasedGround,
  CGround,
  CReactions,
  GroundBehavior,
  Reaction,
  roomItemsLens,
  roomsLens
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
              val tunnelWithBasilisk =
                roomItemsLens.modify(_ + Items.basilisk.ref)(Geography.tunnel)
              for {
                _ <- CReactions.modifyLocationItems(_ - Items.basilisk.ref)
                _ <- Reaction(roomsLens.modify(_ + (Geography.tunnel.ref -> tunnelWithBasilisk)))
                _ <- Reaction(locationLens.set(Geography.tunnel.ref))
                s <- Reaction.messages(Pusher.BasiliskMovedToTunnel)
              } yield s
          }
        }
      )
    )
}
