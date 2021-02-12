package io.github.scalaquest.core

import io.github.scalaquest.core.dictionary.verbs.VerbPrep
import io.github.scalaquest.core.model.Action.Common.{Go, Open, Take}
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
    Set()
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

  val apple: GenericItem = SimpleGenericItem(
    ItemDescription("apple", "big", "red", "juicy"),
    appleItemRef,
    Takeable.builder(),
    Eatable.builder()
  )

  val key: Key = SimpleKey(ItemDescription("key"), keyItemRef, Takeable.builder())

  val door: Door =
    SimpleDoor(
      ItemDescription("door"),
      doorItemRef,
      RoomLink.builder(targetRoom, Direction.North, Some(Openable.builder(requiredKey = Some(key))))
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
    rooms = Map(startRoom.ref -> startRoom, targetRoom.ref -> targetRoom),
    messages = Seq()
  )
}
