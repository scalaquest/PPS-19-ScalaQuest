package io.github.scalaquest.core.dictionary

import io.github.scalaquest.core.model.Action

trait Meaning {
  def action: Action

  def binding: (VerbPrep, Action)
}

trait PairUtils extends Meaning { self: BaseVerb =>
  override def binding: (VerbPrep, Action) = (name, prep) -> action
}
