package io.github.scalaquest.examples.escaperoom

import io.github.scalaquest.core.dictionary.verbs.{Ditransitive, Intransitive, Transitive, Verb}
import io.github.scalaquest.core.dictionary.Dictionary
import io.github.scalaquest.core.model.Action.Common.{Eat, Enter, Go, Inspect, Open, Take}
import io.github.scalaquest.core.model.Direction.{East, North, South, West}

object MyDictionary {

  def dictionary: Dictionary[Item] = Dictionary(myVerbs.toList, myItems.toList)

  // Temporary solution
  def gos: Set[Verb] = {
    Set(
      "north" -> North,
      "south" -> South,
      "east"  -> East,
      "west"  -> West
    ).flatMap((s) =>
      Set(
        s._1.charAt(0).toString -> s._2,
        s._1                    -> s._2
      )
    ).map((s) => Intransitive("go", Go(s._2), Some(s._1)))

  }

  def myVerbs: Set[Verb] =
    Set(
      Transitive("take", Take),
      Transitive("eat", Eat),
      Transitive("pick", Take, Some("up")),
      Ditransitive("open", Open, Some("with")),
      Transitive("open", Open),
      Transitive("enter", Enter),
      Ditransitive("enter", Enter, Some("with")),
      Intransitive("inspect", Inspect)
    ) ++ gos

  println(myVerbs)

  def myItems: Set[Item] =
    Set(
      Items.redApple,
      Items.greenApple,
      Items.apple,
      Items.livingRoomKey,
      Items.livingRoomDoor,
      Items.kitchenDoor
    )
}
