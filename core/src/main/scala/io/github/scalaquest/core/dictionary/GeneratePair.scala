package io.github.scalaquest.core.dictionary

import io.github.scalaquest.core.model.Action

trait GeneratePair {
  def action: Action

  def pair: (BaseVerb, Action)
}

trait PairUtils extends GeneratePair { self: BaseVerb =>
  override def pair: (BaseVerb, Action) = self -> action
}
