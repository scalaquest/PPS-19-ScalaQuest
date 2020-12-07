package io.github.scalaquest.core

import org.scalatest.wordspec.AnyWordSpec
import org.junit.runner.RunWith
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class CoreTest extends AnyWordSpec {

  "A Set" when {
    "empty" should {
      "have size 0" in {
        case class TestCore() extends Core
        assert(Set.empty.isEmpty)
      }

      "produce NoSuchElementException when head is invoked" in {
        assertThrows[NoSuchElementException] {
          Set.empty.head
        }
      }
    }
  }
}