package io.github.scalaquest.examples.pokequest

import io.github.scalaquest.cli.GameCLIApp
import io.github.scalaquest.core.dictionary.verbs.Verb
import io.github.scalaquest.core.model.{Message, StringPusher}
import io.github.scalaquest.core.model.behaviorBased.commons.actioning.CommonVerbs
import io.github.scalaquest.core.model.behaviorBased.simple.SimpleModel

object PokeQuest extends GameCLIApp(SimpleModel) {
  import model._

  override def items: Set[I] = Items.allTheItems

  override def verbs: Set[Verb] = CommonVerbs() ++ Actions.customVerbs

  override def initialMessages: Seq[Message] =
    Seq(
      Messages.Welcome("""
        |Welcome in the PokeQuest Game! You are a Pokémon trainer, from Vermilion City, and you are looking forward to
        |capture new Pokémons. So you start walking to reach the forest, but arrived to the city exit, you notice a
        |Snorlax is blocking the way to the forest :O So, here your adventure starts! Good luck!
        |""".stripMargin)
    )

  override def state: S =
    State(
      actions = verbToAction,
      rooms = Geography.refToRoom,
      items = refToItem,
      bag = Set(Items.flute.ref),
      location = Geography.cityExit.ref
    )

  override def messagePusher: StringPusher = Pusher.defaultPusher
}
