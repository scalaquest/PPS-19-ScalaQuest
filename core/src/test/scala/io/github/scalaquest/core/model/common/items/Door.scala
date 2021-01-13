package io.github.scalaquest.core.model.common.items

import io.github.scalaquest.core.model.Direction.Direction
import io.github.scalaquest.core.model.Room
import io.github.scalaquest.core.model.std.StdModel
import org.scalatest.wordspec.AnyWordSpec
import io.github.scalaquest.core.model.std.StdModel._

class Door extends AnyWordSpec {

  // implementation: this a fragment of the 'storyteller' part, to put into the example
  val kitchen: Room    = Room("kitchen", () => Map[Direction, Room]())
  val livingRoom: Room = Room("living room", () => Map[Direction, Room]())

  val cup: GenericItem = GenericItem("cup", Set(Takeable()))

  val livingKey: Key  = Key("living's key")
  val kitchenKey: Key = Key("kitchen's key")

  val kitchenDoor: StdModel.Door =
    Door("kitchen's door", RoomLink(kitchen, Some(Openable(requiredKey = Some(kitchenKey)))))

  "A Door" when {
    "is seen for the first time" should {
      "be closed" in {}
    }
  }

  "A Door" when {
    "you try to open without the right key" must {
      "remain closed" in {}
    }
  }

  "A Door" when {
    "you open with the right key" should {
      "be opened and admits you to other Room" in {}
    }
  }
}
