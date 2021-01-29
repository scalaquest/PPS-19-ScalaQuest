package io.github.scalaquest.core.dictionary

import io.github.scalaquest.core.model.Action

trait GeneratePair {
  def pair: (String, Action)
}

trait PairUtils extends GeneratePair with Utils { self: BaseVerb =>
  override def pair: (String, Action) = escapedName -> action
}
