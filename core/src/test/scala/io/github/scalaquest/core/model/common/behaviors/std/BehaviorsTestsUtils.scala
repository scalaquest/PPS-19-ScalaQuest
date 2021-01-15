package io.github.scalaquest.core.model.common.behaviors.std

import io.github.scalaquest.core.model.Direction.Direction
import io.github.scalaquest.core.model.Room
import io.github.scalaquest.core.model.std.StdModel.{BehaviorableItem, StdGameState, StdPlayer, StdState}
import monocle.Lens
import monocle.macros.GenLens

object BehaviorsTestsUtils {
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

  val roomsLens: Lens[StdState, Set[Room]] = GenLens[StdState](_.game.rooms)
}
