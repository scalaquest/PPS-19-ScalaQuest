package io.github.scalaquest.core.model.common

import io.github.scalaquest.core.model.TransitiveAction

object Actions {
  case object Take  extends TransitiveAction
  case object Open  extends TransitiveAction
  case object Close extends TransitiveAction
  case object Enter extends TransitiveAction
}
