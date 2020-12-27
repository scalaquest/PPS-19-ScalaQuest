package io.github.scalaquest.core.model

sealed trait Action

trait IntransitiveAction extends Action
trait TransitiveAction   extends Action
trait DitransitiveAction extends Action
