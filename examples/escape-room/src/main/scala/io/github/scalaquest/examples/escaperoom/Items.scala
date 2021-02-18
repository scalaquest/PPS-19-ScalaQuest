package io.github.scalaquest.examples.escaperoom

import io.github.scalaquest.core.model.Direction
import io.github.scalaquest.core.model.ItemDescription.dsl.{d, i}
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
      Eatable.builder(onEatExtra = Reaction.messages(DeliciousMessage))
    )

  val greenApple: Food =
    Food(
      i(d("green"), "apple"),
      Eatable.builder(onEatExtra = Some(Reactions.finishGame(false)))
    )

  val basementHatch: Door = Door(
    i(d("basement"), "hatch"),
    RoomLink.builder(House.basement, Direction.Down)
  )

  val (hatch, hatchKey): (Door, Key) = lockedDoorBuilder(
    keyAddBehaviorsBuilders = Seq(Takeable.builder()),
    keyDesc = i(d("old", "rusty"), "key"),
    doorDesc = i(d("iron"), "hatch"),
    consumeKey = true,
    endRoom = House.livingRoom,
    endRoomDirection = Direction.Up
  )

  val coffer: GenericItem = GenericItem(
    i(d("brown"), "coffer"),
    Seq(
      Openable.builder(onOpenExtra =
        Some(
          Reaction(
            (locationRoomLens composeLens roomItemsLens).modify(_ + hatchKey.ref)
          )
        )
      )
    )
  )

  val (doorway, crowbar): (GenericItem, Key) = openableBuilder(
    keyDesc = i(d("rusty", "heavy"), "crowbar"),
    keyAddBehaviorsBuilders = Seq(Takeable.builder()),
    openableDesc = i(d("big"), "doorway"),
    consumeKey = true,
    onOpenExtra = Some(Reactions.finishGame(true))
  )
}
