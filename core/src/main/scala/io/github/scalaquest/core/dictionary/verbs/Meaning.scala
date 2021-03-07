package io.github.scalaquest.core.dictionary.verbs

import io.github.scalaquest.core.model.Action

trait Meaning extends BaseVerb {
  def action: Action

  def binding: (VerbPrep, Action) = (name, prep) -> action
}
