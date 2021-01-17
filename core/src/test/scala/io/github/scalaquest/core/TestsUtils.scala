package io.github.scalaquest.core

import io.github.scalaquest.core.model.Direction.Direction
import io.github.scalaquest.core.model.common.Actions
import io.github.scalaquest.core.model.{Action, Room}
import io.github.scalaquest.core.model.std.StdModel
import io.github.scalaquest.core.model.std.StdModel.{
  BehaviorableItem,
  Door,
  GenericItem,
  Key,
  StdGameState,
  StdPlayer,
  StdState,
  RoomLink
}
import io.github.scalaquest.core.pipeline.interpreter.ItemRef
import monocle.Lens
import monocle.macros.GenLens

object TestsUtils {
  val startRoom: Room = Room("startRoom", () => Map[Direction, Room]())

  val simpleState: StdState = StdState(
    game = StdGameState(
      player = StdPlayer(bag = Set(), location = startRoom),
      ended = false,
      rooms = Set(startRoom),
      itemsInRooms = Map(startRoom -> Set())
    ),
    messages = Seq()
  )

  val actionsMap: Map[String, Action] = Map[String, Action](
    "take"  -> Actions.Take,
    "bring" -> Actions.Take,
    "open"  -> Actions.Open
  )

  val roomsLens: Lens[StdState, Set[Room]] = GenLens[StdState](_.game.rooms)

  val appleItemRef: ItemRef = new ItemRef {}
  val keyItemRef: ItemRef   = new ItemRef {}
  val doorItemRef: ItemRef  = new ItemRef {}

  val itemsMap: Map[String, ItemRef] = Map[String, ItemRef](
    "apple"     -> appleItemRef,
    "red apple" -> appleItemRef,
    "door"      -> doorItemRef,
    "key"       -> keyItemRef
  )

  val apple: GenericItem = GenericItem("Apple")
  val key: Key           = Key("Key")
  val door: Door         = Door("Door", RoomLink(startRoom))

  val refItemDictionary: Map[ItemRef, BehaviorableItem] = Map(
    appleItemRef -> apple,
    keyItemRef   -> key,
    doorItemRef  -> door
  )
}
