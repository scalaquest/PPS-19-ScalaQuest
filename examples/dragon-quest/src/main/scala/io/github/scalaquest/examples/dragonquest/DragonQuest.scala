package io.github.scalaquest.examples.dragonquest

import io.github.scalaquest.cli.GameCLIApp
import io.github.scalaquest.core.dictionary.verbs.Verb
import io.github.scalaquest.core.model.MessagePusher
import io.github.scalaquest.core.model.behaviorBased.commons.actioning.CommonVerbs
import io.github.scalaquest.core.model.behaviorBased.simple.SimpleModel
import model.State

object DragonQuest extends GameCLIApp(SimpleModel) {

  override def state: S =
    State(
      actions = verbToAction,
      rooms = ChamberOfSecrets.refToRoom,
      items = refToItem,
      location = ChamberOfSecrets.chamberOfSecrets.ref
    )

  override def messagePusher: MessagePusher[String] = Pusher.defaultPusher

  /** The set of verbs used by the application. */
  override def verbs: Set[Verb] = CommonVerbs() ++ Actions.customVerbs

  /** The set of items used by the application. */
  override def items: Set[DragonQuest.I] = Items.allItems
}
