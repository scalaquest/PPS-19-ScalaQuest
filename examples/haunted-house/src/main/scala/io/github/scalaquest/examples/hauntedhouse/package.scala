package io.github.scalaquest.examples

import io.github.scalaquest.core.application.ApplicationStructure
import io.github.scalaquest.core.dictionary.Dictionary
import io.github.scalaquest.core.model.behaviorBased.simple.SimpleModel

package object hauntedhouse extends ApplicationStructure(SimpleModel) {

  override def dictionary: Dictionary[Item] = MyDictionary.dictionary

}
