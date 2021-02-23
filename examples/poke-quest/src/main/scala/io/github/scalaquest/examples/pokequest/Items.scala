package io.github.scalaquest.examples.pokequest

import io.github.scalaquest.core.model.ItemDescription.dsl.{d, i}
import io.github.scalaquest.examples.pokequest.Actions.{Play, Throw, Wake}
import model.{GenericItem, Reaction, CReactions}

object Items {

  def snorlax: GenericItem =
    GenericItem.withGenBehavior(
      i(d("sleeping"), "snorlax"),
      {
        case (Wake, Some(i), _) if i == pokeflute => Reactions.wakeSnorlax
        case _                                    => CReactions.finishGame(false)
      }
    )

  def pokeflute: GenericItem =
    GenericItem.withGenBehavior(
      i("pokeflute"),
      { case (Play, None, s) =>
        if (s.location.items(s).contains(snorlax))
          Reactions.wakeSnorlax
        else
          Reaction.messages(Pusher.FreePlayFlute)
      }
    )

  def pikachu: GenericItem = GenericItem(i("pikachu"))

  def charizard: GenericItem =
    GenericItem.withGenBehavior(
      i("charizard"),
      {
        case (Actions.Attack, Some(i), _) if i == pikachu =>
          Reactions.attackCharizard

        case (Actions.Catch, Some(i), _) if i == pokeball =>
          Reactions.catchCharizard

        case (Actions.Catch, None, _) =>
          Reactions.catchCharizard
      }
    )

  def pokeball: GenericItem =
    GenericItem.withGenBehavior(
      i("pokeball"),
      {
        case (Throw, None, s) if s.location.items(s).contains(charizard) =>
          Reactions.catchCharizard
        case (Throw, Some(i), s) if s.location.items(s).contains(charizard) && i == charizard =>
          Reactions.catchCharizard
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
