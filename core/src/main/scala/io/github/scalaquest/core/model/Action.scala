package io.github.scalaquest.core.model

trait Action {
  def name: String
}

object Actions {
  case class SimpleAction(name: String) extends Action

  object Use     extends SimpleAction("use")
  object Take    extends SimpleAction("take")
  object Inspect extends SimpleAction("inspect")
  object Give    extends SimpleAction("give")
}
