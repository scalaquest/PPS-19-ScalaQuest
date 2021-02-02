package io.github.scalaquest.examples.escaperoom

import io.github.scalaquest.core.dictionary.verbs.{Ditransitive, Intransitive, Transitive, Verb}
import io.github.scalaquest.core.dictionary.{Dictionary}
import io.github.scalaquest.core.model.Action.Common.{Eat, Inspect, Open, Take}

object MyDictionary {

  def dictionary: Dictionary[Item] = Dictionary(myVerbs.toList, myItems.toList)

  def myVerbs: Set[Verb] =
    Set(
      Transitive("take", Take),
      Transitive("eat", Eat),
      Transitive("pick", Take, Some("up")),
      Ditransitive("open", Open, Some("with")),
      Transitive("open", Open),
      Intransitive("inspect", Inspect)
    )

  def myItems: Set[Item] =
    Set(
      Items.redApple,
      Items.greenApple,
      Items.apple
    )
}
