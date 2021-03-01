package io.github.scalaquest.examples.dragonquest

import io.github.scalaquest.cli.GameCLIApp
import io.github.scalaquest.core.dictionary.verbs.Verb
import io.github.scalaquest.core.model.{Message, MessagePusher}
import io.github.scalaquest.core.model.behaviorBased.commons.actioning.CVerbs
import io.github.scalaquest.core.model.behaviorBased.simple.SimpleModel
import model.{State, CMessages}

object DragonQuest extends GameCLIApp(SimpleModel) {

  override def state: S =
    State(
      actions = verbToAction,
      rooms = Geography.refToRoom,
      items = refToItem,
      location = Geography.chamberOfSecrets.ref,
      ground = Ground.ground
    )

  override def initialMessages: Seq[Message] =
    Seq(
      CMessages.Welcome("""
                          |Oh no! You are Harry Potter! You are in the Chamber of Secrets and a terrible Basilisk is in front of you!
                          |You have to save the poor Ginny and kill Tom Riddle (alias He-Who-Is-Not-To-Be-Named).
                          |""".stripMargin)
    )

  override def messagePusher: MessagePusher[String] = Pusher.defaultPusher

  /** The set of verbs used by the application. */
  override def verbs: Set[Verb] = CVerbs() ++ Actions.customVerbs

  /** The set of items used by the application. */
  override def items: Set[I] = Items.allTheItems
}
