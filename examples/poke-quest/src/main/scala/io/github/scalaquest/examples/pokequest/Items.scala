package io.github.scalaquest.examples.pokequest

import io.github.scalaquest.core.model.ItemDescription.dsl.{d, i}
import io.github.scalaquest.core.model.{ItemDescription, ItemRef}
import io.github.scalaquest.examples.pokequest.Actions.{Play, Wake}
import model.{GenericBehavior, GenericItem, Reaction, Reactions, matchEndedLens}

object Items {

  def snorlax: GenericItem =
    GenericItem.withSingleBehavior(
      i(d("sleeping"), "snorlax"),
      GenericBehavior.builder({
        case (Wake, _, Some(i), _) if i == flute => CustomReactions.wakeSnorlaxReaction
        case _                                   => Reactions.finishGame(false)
      })
    )

  def flute: GenericItem =
    GenericItem.withSingleBehavior(
      i("pokeflute"),
      GenericBehavior.builder({ case (Play, _, _, s) =>
        if (s.location.items(s).contains(snorlax)) CustomReactions.wakeSnorlaxReaction
        else Reaction.messages(Pusher.FreePlayFlute)
      })
    )

  def pikachu: GenericItem = GenericItem(i("pikachu"))

  def charizard: GenericItem = {
    new GenericItem { self =>
      var weaken                                = false
      override def description: ItemDescription = i("charizard")
      override def ref: ItemRef                 = ItemRef(i("charizard"))

      override def behaviors: Seq[model.ItemBehavior] =
        Seq(
          GenericBehavior.builder({
            case (Actions.Attack, _, Some(i), _) if i == pikachu && !weaken =>
              weaken = true
              Reaction.messages(Pusher.WeakenCharizard)

            case (Actions.Attack, _, Some(i), _) if i == pikachu && weaken =>
              for {
                _ <- Reaction(matchEndedLens.set(true))
                s <- Reaction.messages(Pusher.KilledCharizard)
              } yield s

            case (Actions.Catch, _, Some(i), _) if i == pokeball && weaken =>
              Reactions.finishGame(true)

            case (Actions.Catch, _, Some(i), _) if i == pokeball && !weaken =>
              for {
                _ <- Reaction(matchEndedLens.set(true))
                s <- Reaction.messages(Pusher.FailedToCaptureCharizard)
              } yield s
          })(self)
        )
    }
  }

  def pokeball: GenericItem = GenericItem(i("pokeball"))

  def allTheItems: Set[I] =
    Set(
      snorlax,
      flute,
      pikachu,
      charizard,
      pokeball
    )
}
