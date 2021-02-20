package io.github.scalaquest.examples.pokequest

import io.github.scalaquest.examples.pokequest.Items.{
  charizard,
  pikachu,
  pokeball,
  pokeflute,
  snorlax
}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class ItemsTest extends AnyWordSpec with Matchers {
  "The Snorlax" should {
    "be woke with the pokeflute" in {
      val react: React = Items.snorlax
        .use(Actions.Wake, Some(Items.pokeflute))(PokeQuest.state)
        .getOrElse(model.Reaction.empty)
      val msgs = react(PokeQuest.state)._2
      msgs should contain(Pusher.SnorlaxWoke)
    }
  }

  "The pokeflute" should {
    "wake the snorlax when played" in {
      val react: React =
        Items.pokeflute.use(Actions.Play)(PokeQuest.state).getOrElse(model.Reaction.empty)
      val msgs = react(PokeQuest.state)._2
      msgs should contain(Pusher.SnorlaxWoke)
    }
  }

  "The Pikachu" should {
    "be into the bag initially" in {
      PokeQuest.state.bag should contain(Items.pikachu)
    }
  }

  "The pokeball" should {
    "be into the bag initially" in {
      PokeQuest.state.bag should contain(Items.pokeball)
    }
  }

  "allTheItems" should {
    "return all the items" in {
      Items.allTheItems shouldBe Set(
        snorlax,
        pokeflute,
        pikachu,
        charizard,
        pokeball
      )
    }
  }
}
