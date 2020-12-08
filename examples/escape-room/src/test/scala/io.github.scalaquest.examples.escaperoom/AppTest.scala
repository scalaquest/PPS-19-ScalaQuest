package io.github.scalaquest.examples.escaperoom

import org.scalatest.wordspec.AnyWordSpec

class AppTest extends AnyWordSpec {

  "A Set" when {
    "empty" should {
      "have size 0" in {
        case class TestCore() extends EscapeRoom
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