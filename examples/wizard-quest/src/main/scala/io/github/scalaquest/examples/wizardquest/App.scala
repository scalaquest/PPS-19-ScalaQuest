package io.github.scalaquest.examples.wizardquest

import io.github.scalaquest.cli.GameCLIApp
import io.github.scalaquest.core.dictionary.verbs.Verb
import io.github.scalaquest.core.model.behaviorBased.commons.actioning.CVerbs
import io.github.scalaquest.core.model.{Message, MessagePusher}
import io.github.scalaquest.core.model.behaviorBased.simple.SimpleModel
import model.{CMessages, State}

/**
 * The entry point of the example.
 */
object App extends GameCLIApp(SimpleModel) {

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
        |Welcome in this ScalaQuest new game! You are Harry Potter, and you are trapped into the Chamber of Secrets!
        |The terrible Basilisk stands in front of you. The Phoenix blinded its eyes, so it is very upset, and really
        |wants to eat you in a single bite! The poor Ginny fainted into the ground, and Tom Riddle (our dear 
        |He-Who-Is-Not-To-Be-Named) is keeping her as an hostage. Save yourself, and save Ginny!
        |""".stripMargin)
    )

  override def messagePusher: MessagePusher[String] = Messages.pusher

  override def verbs: Set[Verb] = CVerbs() ++ Actions.verbs

  override def items: Set[I] = Items.allTheItems
}
