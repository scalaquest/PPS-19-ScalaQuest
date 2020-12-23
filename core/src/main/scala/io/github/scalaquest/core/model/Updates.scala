package io.github.scalaquest.core.model

object Updates {
  type Update[S <: Model#State] = S => S
}
