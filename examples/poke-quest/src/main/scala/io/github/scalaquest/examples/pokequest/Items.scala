/*
 * Copyright (c) 2021 ScalaQuest Team.
 *
 * This file is part of ScalaQuest, and is distributed under the terms of the MIT License as
 * described in the file LICENSE in the ScalaQuest distribution's top directory.
 */

package io.github.scalaquest.examples.pokequest

import io.github.scalaquest.core.model.ItemDescription.dsl.{d, i}
import model.{GenericItem, CReactions}

/**
 * The items required by the example.
 */
object Items {

  def snorlax: GenericItem =
    GenericItem.withGenBehavior(
      i(d("sleeping"), "snorlax"),
      {
        case (Actions.Wake, Some(i), _) if i == pokeflute => Reactions.wakeSnorlax
        case _                                            => CReactions.finishGame(false)
      }
    )

  def pokeflute: GenericItem =
    GenericItem.withGenBehavior(
      i("pokeflute"),
      { case (Actions.Play, None, s) =>
        if (s.location.items(s).contains(snorlax))
          Reactions.wakeSnorlax
        else
          CReactions.addMessage(Messages.FreePlayFlute)
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
        case (Actions.Throw, None, s) if s.location.items(s).contains(charizard) =>
          Reactions.catchCharizard
        case (Actions.Throw, Some(i), s)
            if s.location.items(s).contains(charizard) && i == charizard =>
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
