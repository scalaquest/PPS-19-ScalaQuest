package io.github.scalaquest.examples

import io.github.scalaquest.core.application.ApplicationStructure
import io.github.scalaquest.core.dictionary.verbs.Verb
import io.github.scalaquest.core.model.behaviorBased.commons.actioning.CommonVerbs
import io.github.scalaquest.core.model.behaviorBased.simple.SimpleModel

package object escaperoom extends ApplicationStructure(SimpleModel) {
  override def items: Set[I]    = Items.allTheItems
  override def verbs: Set[Verb] = CommonVerbs()
}
