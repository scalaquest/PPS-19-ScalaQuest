package io.github.scalaquest.core

import io.github.scalaquest.core.dictionary.VerbPrep
import io.github.scalaquest.core.model.Action.Common.{Open, Take}
import io.github.scalaquest.core.model.{Action, Direction, ItemDescription, ItemRef}
import io.github.scalaquest.core.model.behaviorBased.impl.SimpleModel.{
  BehaviorBasedItem,
  Door,
  GenericItem,
  Key,
  Room,
  SimpleDoor,
  SimpleGenericItem,
  SimpleKey,
  SimpleMatchState,
  SimpleOpenable,
  SimplePlayer,
  SimpleRoom,
  SimpleRoomLink,
  SimpleState,
  SimpleTakeable
}

object TestsUtils {

  val startRoom: SimpleRoom = Room(
    "startRoom",
    Map(Direction.North -> targetRoom.ref),
    Set(door.ref, key.ref)
  )

  val targetRoom: SimpleRoom = Room(
    "targetRoom",
    Map(Direction.South -> startRoom.ref),
    Set()
  )

  val actionsMap: Map[VerbPrep, Action] = Map(
    ("take", None)         -> Take,
    ("bring", None)        -> Take,
    ("open", Some("with")) -> Open
  )

  val appleItemRef: ItemRef = ItemRef()
  val keyItemRef: ItemRef   = ItemRef()
  val doorItemRef: ItemRef  = ItemRef()

  val itemsMap: Map[ItemDescription, ItemRef] = Map[ItemDescription, ItemRef](
    ItemDescription("apple", "red") -> appleItemRef,
    ItemDescription("door")         -> doorItemRef,
    ItemDescription("key")          -> keyItemRef
  )

  val apple: GenericItem = SimpleGenericItem(
    ItemDescription("apple", "big", "red", "juicy"),
    appleItemRef,
    SimpleTakeable()
  )
  val key: Key = SimpleKey(ItemDescription("key"), keyItemRef)

  val door: Door =
    SimpleDoor(
      ItemDescription("door"),
      doorItemRef,
      SimpleRoomLink(targetRoom, Some(SimpleOpenable(requiredKey = Some(key))))
    )

  val refItemDictionary: Map[ItemRef, BehaviorBasedItem] = Map(
    appleItemRef -> apple,
    keyItemRef   -> key,
    doorItemRef  -> door
  )

  val simpleState: SimpleState = SimpleState(
    actionsMap,
    matchState = SimpleMatchState(
      player = SimplePlayer(bag = Set(appleItemRef), location = startRoom.ref),
      ended = false,
      items = Map(appleItemRef -> apple, keyItemRef -> key, doorItemRef -> door),
      rooms = Map(startRoom.ref -> startRoom, targetRoom.ref -> targetRoom)
    ),
    messages = Seq()
  )
}
