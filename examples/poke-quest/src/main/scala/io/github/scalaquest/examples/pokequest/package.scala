package io.github.scalaquest.examples

import io.github.scalaquest.core.application.ApplicationStructure
import io.github.scalaquest.core.model.behaviorBased.simple.SimpleModel

package object pokequest extends ApplicationStructure[SimpleModel.type] {
  override val model: SimpleModel.type = SimpleModel
}
