package io.github.scalaquest.examples.escaperoom

import io.github.scalaquest.cli.GameCLIApp
import io.github.scalaquest.core.application.PipelineProvider
import io.github.scalaquest.core.dictionary.verbs.Verb
import io.github.scalaquest.core.model.StringPusher
import io.github.scalaquest.core.model.behaviorBased.commons.actioning.CommonVerbs
import io.github.scalaquest.core.model.behaviorBased.simple.SimpleModel
import io.github.scalaquest.core.pipeline.Pipeline

object EscapeRoom extends GameCLIApp(SimpleModel) with PipelineProvider[SimpleModel.type] {

  override type M = model.type

  override type S = model.S

  override def pipelineBuilder: Pipeline.PartialBuilder[S, M] = makePipeline

  override def items: Set[I] = Items.allTheItems

  override def verbs: Set[Verb] = CommonVerbs()

  override def baseTheory: String = programFromResource("base.pl")

  val welcome: String =
    """
    |Welcome in the Escape Room Game! You have been kidnapped, and you woke up in a
    |gloomy basement. You have to get out of the house to save yourself!
    |""".stripMargin

  override def state: S =
    model.State(
      actions = verbToAction,
      rooms = House.refToRoom,
      items = refToItem,
      location = House.basement.ref,
      welcomeMsg = Some(model.Messages.Welcome(welcome))
    )

  override def messagePusher: StringPusher = Pusher.defaultPusher
}
