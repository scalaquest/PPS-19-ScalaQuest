package io.github.scalaquest.examples.dragonquest

import io.github.scalaquest.core.model.StringPusher
import io.github.scalaquest.core.model.behaviorBased.commons.pushing.CommonStringPusher

object Pusher {

  val defaultPusher: StringPusher = CommonStringPusher(
    model,
    { case _ =>
      "Example"
    }
  )
}
