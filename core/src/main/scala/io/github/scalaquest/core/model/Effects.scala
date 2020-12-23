package io.github.scalaquest.core.model

object Effects {
  type Effect[S <: Model#State] = S => S
}
