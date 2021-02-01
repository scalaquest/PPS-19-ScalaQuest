package io.github.scalaquest.core.dictionary

import io.github.scalaquest.core.dictionary.generators.Item

case class Dictionary(verbs: Set[Verb], items: Set[Item])
