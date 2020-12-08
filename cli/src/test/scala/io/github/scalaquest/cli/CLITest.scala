package io.github.scalaquest.cli

import org.scalatest.wordspec.AnyWordSpec

class CLITest extends AnyWordSpec {

  "A Set" when {
    "empty" should {
      "have size 0" in {
        case class TestCli() extends CLI
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