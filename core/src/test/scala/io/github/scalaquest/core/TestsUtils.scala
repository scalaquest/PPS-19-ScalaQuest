/*
 * Copyright (c) 2021 ScalaQuest Team.
 *
 * This file is part of ScalaQuest, and is distributed under the terms of the MIT License as
 * described in the file LICENSE in the ScalaQuest distribution's top directory.
 */

package io.github.scalaquest.core

import io.github.scalaquest.core.dictionary.verbs.VerbPrep
import io.github.scalaquest.core.model.behaviorBased.commons.actioning.CActions.{Go, Open, Take}
import io.github.scalaquest.core.model.behaviorBased.simple.SimpleModel
import io.github.scalaquest.core.model.{Action, Direction, ItemDescription, ItemRef}

object TestsUtils {
  val model: SimpleModel.type = SimpleModel
  import SimpleModel._

  val startRoom: RM = Room(
    "start room",
    Map(Direction.North -> targetRoom.ref),
    Set(door.ref, key.ref)
  )

  val targetRoom: RM = Room(
    "target room",
    Map(Direction.South -> startRoom.ref),
    Set.empty
  )

  val actionsMap: Map[VerbPrep, Action] = Map(
    ("go north", None)     -> Go(Direction.North),
    ("take", None)         -> Take,
    ("bring", None)        -> Take,
    ("open", Some("with")) -> Open
  )

  val appleItemRef: ItemRef = ItemRef(ItemDescription("apple", "big", "red", "juicy"))
  val keyItemRef: ItemRef   = ItemRef(ItemDescription("key"))
  val doorItemRef: ItemRef  = ItemRef(ItemDescription("door"))

  val itemsMap: Map[ItemDescription, ItemRef] = Map[ItemDescription, ItemRef](
    ItemDescription("apple", "big", "red", "juicy") -> appleItemRef,
    ItemDescription("door")                         -> doorItemRef,
    ItemDescription("key")                          -> keyItemRef
  )

  val apple: GenericItem = GenericItem(
    ItemDescription("apple", "big", "red", "juicy"),
    Seq(Takeable.builder(), Eatable.builder())
  )

  val key: Key = Key(ItemDescription("key"), extraBehavBuilders = Seq(Takeable.builder()))

  val door: Door =
    Door(
      ItemDescription("door"),
      RoomLink.closedLockedBuilder(
        key,
        targetRoom,
        Direction.North
      )
    )

  val refItemDictionary: Map[ItemRef, BehaviorBasedItem] = Map(
    appleItemRef -> apple,
    keyItemRef   -> key,
    doorItemRef  -> door
  )

  val simpleState: S = State(
    actionsMap,
    bag = Set(appleItemRef),
    location = startRoom.ref,
    items = Map(appleItemRef -> apple, keyItemRef -> key, doorItemRef -> door),
    rooms = Map(startRoom.ref -> startRoom, targetRoom.ref -> targetRoom)
  )
}
