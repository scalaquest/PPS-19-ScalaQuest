/*
 * Copyright (c) 2021 ScalaQuest Team.
 *
 * This file is part of ScalaQuest, and is distributed under the terms of the MIT License as
 * described in the file LICENSE in the ScalaQuest distribution's top directory.
 */

package io.github.scalaquest.examples.escaperoom

import io.github.scalaquest.cli.GameCLIApp
import io.github.scalaquest.core.dictionary.verbs.Verb
import io.github.scalaquest.core.model.{Message, StringPusher}
import io.github.scalaquest.core.model.behaviorBased.commons.actioning.CVerbs
import io.github.scalaquest.core.model.behaviorBased.simple.SimpleModel
import model.{CMessages, State}

object App extends GameCLIApp(SimpleModel) {

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
      rooms = Geography.refToRoom,
      items = refToItem,
      location = Geography.basement.ref
    )

  override def messagePusher: StringPusher = Messages.pusher
}
