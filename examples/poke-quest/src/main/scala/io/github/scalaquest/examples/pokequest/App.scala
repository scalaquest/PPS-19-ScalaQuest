package io.github.scalaquest.examples.pokequest

import io.github.scalaquest.cli.GameCLIApp
import io.github.scalaquest.core.dictionary.verbs.Verb
import io.github.scalaquest.core.model.{Message, StringPusher}
import io.github.scalaquest.core.model.behaviorBased.commons.actioning.CVerbs
import io.github.scalaquest.core.model.behaviorBased.simple.SimpleModel
import model.{CMessages, State}

/**
 * The entry point of the example.
 */
object App extends GameCLIApp(SimpleModel) {

  override def items: Set[I] = Items.allTheItems

  override def verbs: Set[Verb] = CVerbs() ++ Actions.verbs

  override def initialMessages: Seq[Message] =
    Seq(
      CMessages.Welcome("""
        |Welcome in the PokeQuest Game! You are a Ash, from Vermilion City, and you are looking forward to
        |capture new Pokemons. Your faithful Pikachu is always following you, in your adventures.
        |So you start walking to reach the forest, but arrived to the city exit, you notice a
        |Snorlax is blocking the way to the forest :O So, here your adventure starts! Good luck!
        |""".stripMargin)
    )

  override def state: S =
    State(
      actions = verbToAction,
      rooms = Geography.refToRoom,
      items = refToItem,
      bag = Set(Items.pokeflute.ref, Items.pokeball.ref, Items.pikachu.ref),
      location = Geography.vermillionCity.ref
    )

  override def messagePusher: StringPusher = Messages.pusher
}
