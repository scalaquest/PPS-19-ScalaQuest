/*
 * Copyright (c) 2021 ScalaQuest Team.
 *
 * This file is part of ScalaQuest, and is distributed under the terms of the MIT License as
 * described in the file LICENSE in the ScalaQuest distribution's top directory.
 */

package io.github.scalaquest.examples.escaperoom

import io.github.scalaquest.core.model.Direction
import io.github.scalaquest.core.model.ItemDescription.dsl.{d, i}
import io.github.scalaquest.examples.escaperoom.Messages.DeliciousMessage
import model.{
  Food,
  Key,
  Door,
  Chest,
  Takeable,
  CReactions,
  GenericItem,
  Openable,
  BehaviorBasedItem
}

/**
 * The items required by the example.
 */
object Items {

  val redApple: Food =
    Food(
      i(d("red"), "apple"),
      onEatExtra = CReactions.addMessage(DeliciousMessage)
    )

  val greenApple: Food =
    Food(
      i(d("green"), "apple"),
      onEatExtra = CReactions.finishGame(false)
    )

  val basementHatch: Door = Door.createOpened(
    i(d("basement"), "hatch"),
    Geography.basement,
    Direction.Down
  )

  val (hatch, hatchKey): (Door, Key) = Door.createLockedWithKey(
    key = Key(
      i(d("old", "rusty"), "key"),
      extraBehavBuilders = Seq(Takeable.builder())
    ),
    doorDesc = i(d("iron"), "hatch"),
    endRoom = Geography.livingRoom,
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

  val doorway: GenericItem = GenericItem.withSingleBehavior(
    i(d("big"), "doorway"),
    Openable.lockedBuilder(crowbar, onOpenExtra = CReactions.finishGame(win = true))
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
