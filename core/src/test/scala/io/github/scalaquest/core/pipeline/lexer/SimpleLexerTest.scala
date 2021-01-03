package io.github.scalaquest.core.pipeline.lexer

import org.scalatest.wordspec.AnyWordSpec

class SimpleLexerTest extends AnyWordSpec {
  "A simple lexer" when {
    "provided an empty string" should {
      "return an empty sequence of tokens" in {
        assert(SimpleLexer.tokenize("").tokens.isEmpty)
      }
    }
    "provided a string" should {
      "return a sequence of tokens" in {
        val str = "take the apple"
        assert(SimpleLexer.tokenize(str).tokens == Seq("take", "the", "apple"))
      }

      "transform it to lower case" in {
        val str = "TAKE THE APPLE"
        assert(SimpleLexer.tokenize(str).tokens == Seq("take", "the", "apple"))
      }
    }
    "provided an unary string" should {
      "return an unary list" in {
        assert(SimpleLexer.tokenize("inspect").tokens == Seq("inspect"))
      }
    }
  }
}
