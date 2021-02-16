package io.github.scalaquest.core.model.behaviorBased.commons.builders

import io.github.scalaquest.core.TestsUtils
import io.github.scalaquest.core.model.ItemDescription
import org.scalatest.wordspec.AnyWordSpec

class OpKeyBuilderExtTest extends AnyWordSpec {
  import TestsUtils.model._

  "A OpKeyBuilder" should {
    val (openableItem, key) = OpenableBuilder(
      keyDesc = ItemDescription("key")
    )(
      openableDesc = ItemDescription("door")
    )

    "return a tuple with a working key-openableItem pair" in {
      openableItem.behaviors.head match {
        case openable: Openable if openable.requiredKey.get == key => succeed
        case _                                                     => fail("The key do not matches")
      }
    }
  }
}
