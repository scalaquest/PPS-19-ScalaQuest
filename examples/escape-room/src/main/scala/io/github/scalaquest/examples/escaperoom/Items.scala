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
      Eatable.builder(onEatExtra = Some(messageLens.modify(_ :+ DeliciousMessage)(_)))
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

  val (hatch, hatchKey): (Door, Key) = doorKeyBuilder(
    doorDesc = i(d("iron"), "hatch"),
    keyDesc = i(d("old", "rusty"), "key"),
    consumeKey = true,
    endRoom = House.livingRoom,
    endRoomDirection = Direction.Up,
    keyAddBehaviorsBuilders = Seq(Takeable.builder())
  )

  val coffer: GenericItem = GenericItem(
    i(d("brown"), "coffer"),
    Seq(Openable.builder(onOpenExtra = Some(state => {
      val updLocation = roomItemsLens.modify(_ + hatchKey.ref)(state.location)
      roomsLens.modify(_ + (updLocation.ref -> updLocation))(state)
    })))
  )

  val (doorway, crowbar): (GenericItem, Key) = openableWithKeyBuilder(
    openableDesc = i(d("big"), "doorway"),
    keyDesc = i(d("rusty", "heavy"), "crowbar"),
    consumeKey = true,
    keyAddBehaviorsBuilders = Seq(Takeable.builder()),
    onOpenExtra = Some(Reactions.finishGame(true))
  )
}
