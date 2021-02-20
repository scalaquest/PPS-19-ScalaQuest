package io.github.scalaquest.examples.pokequest

import io.github.scalaquest.core.model.ItemDescription.dsl.{d, i}
import io.github.scalaquest.examples.pokequest.Actions.{Play, Throw, Wake}
import model.{GenericItem, Reaction, Reactions}

object Items {

  def snorlax: GenericItem =
    GenericItem.withGenBehavior(
      i(d("sleeping"), "snorlax"),
      {
        case (Wake, _, Some(i), _) if i == pokeflute => CustomReactions.wakeSnorlax
        case _                                       => Reactions.finishGame(false)
      }
    )

  def pokeflute: GenericItem =
    GenericItem.withGenBehavior(
      i("pokeflute"),
      { case (Play, _, _, s) =>
        if (s.location.items(s).contains(snorlax))
          CustomReactions.wakeSnorlax
        else
          Reaction.messages(Pusher.FreePlayFlute)
      }
    )

  def pikachu: GenericItem = GenericItem(i("pikachu"))

  def charizard: GenericItem =
    GenericItem.withGenBehavior(
      i("charizard"),
      {
        case (Actions.Attack, _, Some(i), _) if i == pikachu =>
          CustomReactions.attackCharizard

        case (Actions.Catch, _, Some(i), _) if i == pokeball =>
          CustomReactions.catchCharizard

        case (Actions.Catch, _, None, _) =>
          CustomReactions.catchCharizard
      }
    )

  def pokeball: GenericItem =
    GenericItem.withGenBehavior(
      i("pokeball"),
      {
        case (Throw, _, None, s) if s.location.items(s).contains(charizard) =>
          CustomReactions.catchCharizard
        case (Throw, _, Some(i), s) if s.location.items(s).contains(charizard) && i == charizard =>
          CustomReactions.catchCharizard
      }
    )

  def allTheItems: Set[I] =
    Set(
      snorlax,
      pokeflute,
      pikachu,
      charizard,
      pokeball
    )
}
