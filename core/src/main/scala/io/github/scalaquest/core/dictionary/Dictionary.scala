package io.github.scalaquest.core.dictionary

import io.github.scalaquest.core.model.Model

case class Dictionary[I <: Model#Item](verbs: List[Verb], items: List[I])
