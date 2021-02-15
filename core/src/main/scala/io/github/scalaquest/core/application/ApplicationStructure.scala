package io.github.scalaquest.core.application

import io.github.scalaquest.core.model.Model

trait ApplicationStructure[M0 <: Model] {

  val model: M0

  type M        = model.type
  type S        = model.S
  type I        = model.I
  type RM       = model.RM
  type G        = model.G
  type Reaction = model.Reaction

}
