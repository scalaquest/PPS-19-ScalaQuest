package io.github.scalaquest.examples.escaperoom

import io.github.scalaquest.core.model.Direction
import io.github.scalaquest.core.model.ItemDescription.dsl.{d, i}
import io.github.scalaquest.examples.escaperoom.Pusher.DeliciousMessage

object Items {

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

  val chest: Chest = Chest.createUnlocked(
    i(d("brown"), "chest"),
    Set(hatchKey)
  )

  val crowbar: Key =
    Key(
      i(d("rusty", "heavy"), "crowbar"),
      extraBehavBuilders = Seq(Takeable.builder())
    )

  val doorway: GenericItem = GenericItem(
    i(d("big"), "doorway"),
    Seq(Openable.lockedBuilder(crowbar, onOpenExtra = Reactions.finishGame(win = true)))
  )

  def allTheItems: Set[BehaviorBasedItem] =
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
}
