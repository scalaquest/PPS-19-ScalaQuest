package io.github.scalaquest.examples.escaperoom

import io.github.scalaquest.core.model.Direction
import io.github.scalaquest.core.model.ItemDescription.dsl.{d, i}
import io.github.scalaquest.examples.escaperoom.Pusher.DeliciousMessage

object Items {

  def allTheItems: Set[I] =
    Set(
      Items.redApple,
      Items.greenApple,
      Items.chest,
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
      Eatable.builder(onEatExtra = Reactions.finishGame(false))
    )

  val basementHatch: Door = Door(
    i(d("basement"), "hatch"),
    RoomLink.builder(House.basement, Direction.Down)
  )

  val (hatch, hatchKey): (Door, Key) = Door.createLockedWithKey(
    key = Key(
      i(d("old", "rusty"), "key"),
      extraBehavBuilders = Seq(Takeable.builder())
    ),
    doorDesc = i(d("iron"), "hatch"),
    endRoom = House.livingRoom,
    endRoomDirection = Direction.Up
  )

  val chest: GenericItem = GenericItem(
    i(d("brown"), "chest"),
    Seq(
      Openable.unlockedBuilder(
        onOpenExtra = Reaction(
          (locationRoomLens composeLens roomItemsLens).modify(_ + hatchKey.ref)
        )
      )
    )
  )

  val (doorway, crowbar): (GenericItem, Key) = openableBuilder(
    key = Key(
      i(d("rusty", "heavy"), "crowbar"),
      extraBehavBuilders = Seq(Takeable.builder())
    ),
    openableDesc = i(d("big"), "doorway"),
    onOpenExtra = Reactions.finishGame(true)
  )
}
