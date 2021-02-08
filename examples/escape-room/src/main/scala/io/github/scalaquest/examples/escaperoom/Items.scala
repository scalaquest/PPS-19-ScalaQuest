package io.github.scalaquest.examples.escaperoom

import io.github.scalaquest.core.model.ItemDescription.dsl.{d, i}
import io.github.scalaquest.core.model.Direction
import io.github.scalaquest.examples.escaperoom.House.{basement, livingRoom}
import io.github.scalaquest.examples.escaperoom.Pusher.DeliciousMessage

object Items {

  def allTheItems: Set[I] =
    Set(
      Items.redApple,
      Items.greenApple,
      Items.coffer,
      Items.hatchKey,
      Items.hatch,
      Items.doorway,
      Items.crowbar,
      Items.basementHatch
    )

  import model._

  val redApple: Food =
    Food(
      i(d("red"), "apple"),
      SimpleEatable(onEatExtra = Some(messageLens.modify(_ :+ DeliciousMessage)(_)))
    )

  val greenApple: Food =
    Food(
      i(d("green"), "apple"),
      SimpleEatable(onEatExtra =
        Some(
          _.applyReactions(
            messageLens.modify(_ :+ Lose),
            matchEndedLens.set(true)
          )
        )
      )
    )

  val basementHatch: Door = Door(
    i(d("basement"), "hatch"),
    RoomLink(basement, Direction.Down)
  )

  val (hatch, hatchKey): (Door, Key) = doorKeyBuilder(
    doorDesc = i(d("iron"), "hatch"),
    keyDesc = i(d("old", "rusty"), "key"),
    consumeKey = true,
    endRoom = livingRoom,
    endRoomDirection = Direction.Up,
    keyAddBehaviors = Seq(SimpleTakeable()),
    onOpenExtra = Some(state => {
      state.applyReactions(
        messageLens.set(
          Seq(
            Print(
              "The key slides into the lock easily. With great effort, " +
                "I open the hatch and glimpse a dusty living room above me."
            )
          )
        )
      )
    })
  )

  val coffer: GenericItem = GenericItem(
    i(d("brown"), "coffer"),
    Seq(Openable(onOpenExtra = Some(state => {
      val updLocation = roomItemsLens.modify(_ + hatchKey.ref)(state.location)
      state.applyReactions(
        roomsLens.modify(_ + (updLocation.ref -> updLocation)),
        messageLens.set(
          Seq(
            Print(
              "The chest swung open. There is an old rusty key inside it. " +
                "Maybe it could be useful."
            )
          )
        )
      )
    })))
  )

  val (doorway, crowbar): (GenericItem, Key) = openableWithKeyBuilder(
    openableDesc = i(d("big"), "doorway"),
    keyDesc = i(d("rusty", "heavy"), "crowbar"),
    consumeKey = true,
    keyAddBehaviors = Seq(SimpleTakeable()),
    onOpenExtra = Some(
      _.applyReactions(
        messageLens.modify(_ :+ Win),
        matchEndedLens.set(true)
      )
    )
  )
}
