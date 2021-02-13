package io.github.scalaquest.core.model.behaviorBased.commons.actioning

import io.github.scalaquest.core.dictionary.verbs.{
  BaseVerb,
  Ditransitive,
  Intransitive,
  Transitive,
  Verb
}
import io.github.scalaquest.core.model.Direction
import io.github.scalaquest.core.model.behaviorBased.commons.actioning.CommonActions.{
  Eat,
  Enter,
  Go,
  Inspect,
  Open,
  Take
}

object CommonVerbs {

  private def gos: Set[Verb] = {
    Set(
      "north" -> Direction.North,
      "south" -> Direction.South,
      "east"  -> Direction.East,
      "west"  -> Direction.West,
      "up"    -> Direction.Up,
      "down"  -> Direction.Down
    ).flatMap(s =>
      Set(
        s._1.charAt(0).toString -> s._2,
        s._1                    -> s._2
      )
    ).map(s => Intransitive("go", Go(s._2), Some(s._1)))
  }

  def apply(): Set[Verb] =
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
}
