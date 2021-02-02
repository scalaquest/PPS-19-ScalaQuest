package io.github.scalaquest.core.dictionary

import io.github.scalaquest.core.dictionary.verbs.Verb

case class Dictionary[I <: Item](verbs: List[Verb], items: List[I])
