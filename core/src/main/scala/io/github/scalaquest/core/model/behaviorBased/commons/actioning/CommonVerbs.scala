package io.github.scalaquest.core.model.behaviorBased.commons.actioning

import io.github.scalaquest.core.dictionary.verbs.{Ditransitive, Intransitive, Transitive, Verb}
import io.github.scalaquest.core.model.Direction
import io.github.scalaquest.core.model.behaviorBased.commons.actioning.CommonActions._

/**
 * [[Verb]] s already implemented in the game.
 */
object CommonVerbs {

  private def movements: Set[Verb] = {
    Set(
      "north" -> Direction.North,
      "south" -> Direction.South,
      "east"  -> Direction.East,
      "west"  -> Direction.West,
      "up"    -> Direction.Up,
      "down"  -> Direction.Down
    ).flatMap { case (name, dir) =>
      Set(
        name.charAt(0).toString -> dir,
        name                    -> dir
      )
    }.map { case (name, dir) => Intransitive("go", Go(dir), Some(name)) }
  }

  /**
   * All the common verbs.
   * @return
   *   all the common verbs.
   */
  def apply(): Set[Verb] =
    Set(
      Transitive("take", Take),
      Transitive("eat", Eat),
      Transitive("pick", Take, Some("up")),
      Ditransitive("open", Open, Some("with")),
      Transitive("open", Open),
      Transitive("enter", Enter),
      Ditransitive("enter", Enter, Some("with")),
      Intransitive("inspect", Inspect),
      Intransitive("inspect", InspectBag, Some("bag"))
    ) ++ movements
}
