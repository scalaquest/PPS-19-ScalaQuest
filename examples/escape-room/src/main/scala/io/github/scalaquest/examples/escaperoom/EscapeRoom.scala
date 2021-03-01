package io.github.scalaquest.examples.escaperoom

import io.github.scalaquest.cli.GameCLIApp
import io.github.scalaquest.core.dictionary.verbs.Verb
import io.github.scalaquest.core.model.{Message, StringPusher}
import io.github.scalaquest.core.model.behaviorBased.commons.actioning.CVerbs
import io.github.scalaquest.core.model.behaviorBased.simple.SimpleModel
import model.{CMessages, State}

object EscapeRoom extends GameCLIApp(SimpleModel) {

  override def items: Set[I] = Items.allTheItems

  override def verbs: Set[Verb] = CVerbs()

  override def initialMessages: Seq[Message] =
    Seq(
      CMessages.Welcome("""
        |Welcome in the Escape Room Game! You have been kidnapped, and you woke up in a
        |gloomy basement. You have to get out of the house to save yourself!
        |""".stripMargin)
    )

  override def state: S =
    State(
      actions = verbToAction,
      rooms = House.refToRoom,
      items = refToItem,
      location = House.basement.ref
    )

  override def messagePusher: StringPusher = Pusher.defaultPusher
}
